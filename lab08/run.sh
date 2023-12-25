#!/bin/bash

cd preprocessing

python3 -m pip install pipenv
python3 -m pipenv install
python3 -m pipenv shell
clear

cd src
python3 main.py
cd ../..

cp preprocessing/data/test.txt gaussian-elimination/src/main/resources/test.txt

cd gaussian-elimination
mvn clean compile exec:java
cd ..
