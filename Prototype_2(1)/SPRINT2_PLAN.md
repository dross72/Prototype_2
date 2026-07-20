# Sprint 2 Master Plan - one doc we all work from

Due Monday July 20, 9:00 PM on D2L. Two dropboxes: Prototype 2 and System Design Documents.

## Requirements checklist (from the Sprint 2 doc + Gesick's answers)

| # | Requirement | Status | Where |
|---|-------------|--------|-------|
| 1 | Login with username + password, nothing else | DONE | LoginPanel, reps come from data/reps.txt |
| 2 | 4 sample reps preloaded from the spec | DONE | data/reps.txt, logins in README |
| 3 | At least 300 inventory items | DONE | 1,809 active products load from data/MPL |
| 4 | Restricted / incomplete products never shown | DONE | MasterProductList.loadProducts filters them |
| 5 | Reps see only ID, description, brand, container | DONE | Product class only keeps those fields |
| 6 | Add a new customer (form matches client's sample) | DONE | CustomerFormPanel, saves to customers.txt |
| 7 | Take a customer order | DONE | OrderBuilderPanel |
| 8 | Remove ONE item without cancelling the order | DONE | Remove Selected Item button |
| 9 | Whole customer list visible, own customers open free, others need override code | DONE | override code is ACME2026 |
| 10 | Completed order display screen | DONE | ReviewPanel |
| 11 | Submit = "transmit" by writing a file (JSON) | DONE | orders/ folder, one JSON per order |
| 12 | No pricing, payment, tax, bills of lading, routing anywhere | DONE | nothing like that exists in the app |
| 13 | Runs standalone, no web, files simulate the database | DONE | data/ folder |
| 14 | Javadoc style comments | DONE | all classes |
| 15 | Executable jar | DONE | RepApp.jar, built for Java 17 |
| 16 | README with existing username/passwords | DONE | README.md |
| 17 | Zipped project folder | John builds it at submission time | |
| 18 | System Design doc | IN PROGRESS, see split below | template from D2L content |

## Who owns what

| Person | Task | Status |
|--------|------|--------|
| Dylan | Review + merge PR #1. Any extra logic you finish, push on your own branch and open a PR so main keeps compiling | PR open |
| John | GUI, jar, README, assembling the final design doc, D2L submission | app done |
| Brianna | Doc sections in SECTIONS_Brianna.docx (cover info, report formats, database tables, tech support) | file sent in chat |
| Jaden | Doc section in SECTIONS_Jaden.docx (class documentation tables) | file sent in chat, waiting on a thumbs up |

## Ground rules for tomorrow

1. main must always compile. Push on a branch, open a PR, we merge after a look.
2. Doc sections come back to John by mid afternoon so there's time to assemble and submit with buffer.
3. Anything not delivered by ~6 PM, John covers it so we don't miss the deadline, and we sort credit out in peer reviews.
4. John submits both dropboxes unless someone objects before then.

## How to run the app

Pull the john-gui-v2 branch (or main after the PR merges), then from the project folder:

    java -jar RepApp.jar

Logins: SMITHERSJ_57842, JEFFERIEST_1548, JAMESL_82312345, MADDUNG_5482103, all with password beer123. Manager override code: ACME2026.
