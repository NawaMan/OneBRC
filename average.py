import sys

def calculate_average(numbers):
    if not numbers:
        return None
    return sum(numbers) / len(numbers)

if __name__ == "__main__":
    numbers = []
    for line in sys.stdin:
        try:
            number = float(line.strip())
            print(number)
            numbers.append(number)
        except ValueError:
            print(f"Skipping invalid input: {line.strip()}", file=sys.stderr)

    average = calculate_average(numbers)
    count = len(numbers)
    min = min(numbers)
    max = max(numbers)
    print(f"The count is: {count}")
    print(f"The min is: {min}")
    print(f"The max is: {max}")
    if average is not None:
        print(f"The average is: {average}")
    else:
        print("No valid numbers were provided.")
