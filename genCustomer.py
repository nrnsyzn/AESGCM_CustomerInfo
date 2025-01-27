# This code is written to generate fake customer data for testing
import csv
import random
from faker import Faker

# Initialize Faker
fake = Faker()

# Generate 200 rows of customer data
customers = []
for i in range(1, 201):
    first_name = fake.first_name()
    last_name = fake.last_name()
    email = fake.email()
    phone_number = fake.phone_number()
    password = fake.password(length=10)  # Random password
    total_member_points = random.randint(0, 1000)  # Random points between 0 and 1000

    customer = {
        "id": i,
        "name": f"{first_name} {last_name}",
        "phone_number": phone_number,
        "email": email,
        "password": password,
        "total_member_points": total_member_points
    }
    customers.append(customer)

# Write the data to a CSV file
filename = "customers.csv"
with open(filename, mode="w", newline="") as file:
    fieldnames = ["id", "name", "phone_number", "email", "password", "total_member_points"]
    writer = csv.DictWriter(file, fieldnames=fieldnames)
    
    writer.writeheader()
    for customer in customers:
        writer.writerow(customer)

print(f"CSV file '{filename}' with 200 customer records has been created.")
