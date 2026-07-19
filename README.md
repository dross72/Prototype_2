# ACME Distributing - Sales Rep Prototype (Sprint 2)

SWE 3313 group project. Desktop Java Swing app for the sales representative
component of the distributor ordering system.

## How to run

Java 17 or newer. From this folder:

    java -jar RepApp.jar

Run it from the project folder so the app can find the data files.

## Logins that already exist

| Rep | Username | Password |
|-----|----------|----------|
| John Smithers | SMITHERSJ_57842 | beer123 |
| Thomas Jefferies | JEFFERIEST_1548 | beer123 |
| Larry James | JAMESL_82312345 | beer123 |
| Greg Maddun | MADDUNG_5482103 | beer123 |

Manager override code (for opening another rep's customer): ACME2026

## What it does

- Login with username and password (no remember me / forgot password, per client)
- Home screen: start new order, add new customer, my customers, recent order history
- New customer form matching the client's sample form (name, address, beer
  license, form of payment, loading dock, delivery constraints, POC, phone)
- Order builder: search 1,809 active products from the master product list,
  add line items with quantity, remove a single item without cancelling
  the order
- Review screen shows the completed order; Submit "transmits" it by writing
  a JSON file into the orders folder (simulates sending to the office)

## Data files (simulated database)

- data/reps.txt - sales reps and passwords
- data/customers.txt - customer records (new customers append here)
- data/MPL - master product list (blank/Restricted rows are filtered out)
- orders/ - one JSON file per submitted order

## Out of scope for Sprint 2 (per client)

Pricing, payment, sales tax, bills of lading, delivery scheduling and routing.
