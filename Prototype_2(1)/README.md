# ACME Distributing - Sales Rep Prototype (Sprint 2)

SWE 3313 group project. Desktop Java Swing app for the sales representative
component of the distributor ordering system.

## How to run

Needs Java 17 or newer (check with java -version, install from adoptium.net if missing).

The one rule for all three options: run it FROM the project folder (the folder
containing src and data). If the product list comes up empty, the app was
started from somewhere else and can't find the data folder.

Option 1, the jar:

    java -jar RepApp.jar

Option 2, compile from source (if you pulled the repo, the jar isn't checked in):

    javac -d build src/*.java
    java -cp build Main

Option 3, IntelliJ:
1. File > Open and pick this folder
2. Right click src > Mark Directory as > Sources Root if it isn't already
3. Open Main.java and hit the run arrow (working directory defaults to the
   project folder, which is what the app expects)

## Logins that already exist

| Rep | Username | Password |
|-----|----------|----------|
| John Smithers | SMITHERSJ_57842 | beer123 |
| Thomas Jefferies | JEFFERIEST_1548 | beer123 |
| Larry James | JAMESL_82312345 | beer123 |
| Greg Maddun | MADDUNG_5482103 | beer123 |

Manager override code (for opening another rep's customer): ACME2026

## What it does

- Login with username and password only, no remember me or forgot password
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
