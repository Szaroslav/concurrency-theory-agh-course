# Concurrent gaussian elimination
Home assignment 3

## How to run (doesn't work)
1. Be in the root directory (`lab08`).
2. Run*:
  ```bash
  ./run.sh
  ```
  _*If you cannot run the script, grant an execution permission:_
  ```bash
  chmod +x ./run.sh
  ```

## Description
This project extends project from laboratory 6. Presents how concurrent Gaussian elimination alghorithm may be grouped and run concurrently.

The project is separated into 2 core parts:
- Preprocessing, extension of laboratory 6 - main goal is to create a Foata Normal Form to use it further in the implementation project.
- Gaussian elimination - project written in Java to solve any proper system of equations.
