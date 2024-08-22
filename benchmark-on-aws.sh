#!/bin/bash

export AWS_PAGER=

set -e

TIMEOUT=600  # 5 minutes in seconds
INTERVAL=5   # Interval between checks in seconds
STACKNAME=OneBillionRowChallenge
PEMFILE=$HOME/secrets/OneBillionRowChallenge.pem

echo "Creating Cloudformation Stack: $STACKNAME"
aws cloudformation                                   \
    create-stack                                     \
    --stack-name    "$STACKNAME"                     \
    --template-body file://$(pwd)/aws-benchmark.yaml \
    --capabilities  CAPABILITY_IAM

echo "Waiting for the Cloudformation Stack to be created."
while true ; do
    CFSTATUS=`aws cloudformation describe-stacks --stack-name "$STACKNAME" --query "Stacks[0].StackStatus" --output text`
    echo "$CFSTATUS"
    if [ "$CFSTATUS" == "CREATE_COMPLETE" ]; then break; fi
    sleep 1
done

echo ""
echo "Extracting the instance ID."
INSTID=`aws cloudformation describe-stacks --stack-name "$STACKNAME" --query 'Stacks[0].Outputs[0].OutputValue' --output text`
echo "Instance ID: $INSTID"

echo ""
echo "Extracting the IP address."
IPADDR=`aws ec2 describe-instances --instance-ids "$INSTID" --query "Reservations[0].Instances[0].PublicIpAddress" --output text`
echo "Instance IP address: $IPADDR"

sleep 10

echo ""
echo "Waiting for all the needed software to installed and the comparison to start."
REMAINING=$TIMEOUT
while ! ssh -i "$PEMFILE" "ubuntu@$IPADDR" -o StrictHostKeyChecking=no "test -f /home/ubuntu/compare.log"; do
    if [ $REMAINING -le 0 ]; then
        echo "Timeout reached: compare.log was not found within 10 minutes."
        exit 1
    fi
    
    echo "Waiting for software installation and comparison to start... Time remaining: $REMAINING seconds"
    sleep $INTERVAL
    REMAINING=$((REMAINING - INTERVAL))
done

sleep 10

echo ""
echo "Compare result: "
ssh -i "$PEMFILE" "ubuntu@$IPADDR" -o StrictHostKeyChecking=no "tail -n 200 -f /home/ubuntu/compare.log | sed '/All match.|Differences found./q'"
echo ""

sleep 10

echo ""
echo "Performance result: "
ssh -i "$PEMFILE" "ubuntu@$IPADDR" -o StrictHostKeyChecking=no "tail -n 200 -f /home/ubuntu/benchmark.log | sed '/All Done./q'"
echo ""

echo ""
echo "What to go from here?"
echo "To run another performance check: 'ssh -i \"$PEMFILE\" \"ubuntu@$IPADDR\" \"/home/ubuntu/\"'"
echo "To delete the stack:              'aws cloudformation delete-stack --stack-name \"$STACKNAME\"'"

