# Parking Lot System

A small Java model of a multi-storey car park. A gate registers cars, finds a free space for each one (on its preferred floor if possible), and releases the space when the car leaves. The project comes with JUnit 5 tests.

## Features

- **Multi-floor layout.** The lot is a grid of floors × spaces. You can print it as text: `S` is a small car, `L L` is a large car, and `X` is a free space.
- **Two car sizes.** Small cars take one space. Large cars take two spaces next to each other.
- **Preferred floor.** Each car has a preferred floor. If that floor is full, the gate searches outward to the floors below and above, alternating between them.
- **Register / deregister.** Cars get a ticket ID when they enter. Deregistering with an unknown ticket throws a `CarNotRegisteredError`.

Example output for a 3-floor, 5-space lot (top floor printed first):

```
X X X X X
X S X X X
S X L L X
```

## Project structure

```
├── vehicle/
│   ├── Car.java                 # license plate, size, preferred floor, ticket ID
│   └── Size.java                # SMALL / LARGE
├── parking/
│   ├── ParkingLot.java          # floor plan + text representation
│   ├── ParkingLotTest.java
│   └── facility/
│       ├── Space.java           # a single parking space
│       ├── Gate.java            # finding spaces, registering / deregistering cars
│       ├── GateTest.java
│       └── CarNotRegisteredError.java
├── *StructureTest.java          # structural tests (class/method signatures)
├── ParkingLotTestSuite.java     # runs all structural + functional tests
├── check.cmd                    # test runner script (Windows and Linux/macOS)
└── *.jar                        # JUnit 5 + CheckThat testing libraries
```

## Running the tests

Requires **Java 21 or newer**.

Windows (PowerShell / cmd):

```
.\check.cmd ParkingLotTestSuite.java ParkingLotTestSuite
```

Linux / macOS:

```
bash check.cmd ParkingLotTestSuite.java ParkingLotTestSuite
```

To run a single test class, pass its file and class name, for example:

```
.\check.cmd parking/facility/GateTest.java parking.facility.GateTest
```

## About

Built as a university programming assignment, to practise Java packages, classes, exceptions and unit testing with JUnit 5.
