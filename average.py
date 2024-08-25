import sys
import argparse

def calculate_average(numbers):
    if not numbers:
        return None
    return sum(numbers) / len(numbers)

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Process some numbers.")
    parser.add_argument('--prefix', type=str, default='', help="Prefix to add to the result output")
    args = parser.parse_args()
    
    numbers = []
    for line in sys.stdin:
        try:
            number = float(line.strip())
            print(number)
            numbers.append(number)
        except ValueError:
            print(f"Skipping invalid input: {line.strip()}", file=sys.stderr)
            
    if args.prefix:
        prefix = args.prefix
    else:
        prefix = ""
    
    average = calculate_average(numbers)
    count = len(numbers)
    min = min(numbers)
    max = max(numbers)
    print(f"{prefix}The count is: {count}")
    print(f"{prefix}The min is: {min}")
    print(f"{prefix}The max is: {max}")
    if average is not None:
        print(f"{prefix}The average is: {average}")
    else:
        print("No valid numbers were provided.")
