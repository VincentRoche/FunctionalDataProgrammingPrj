#!/bin/bash

busId=1
fuel=100
seats=50
line=1
nextStop=1
nextStopDistance=1000
broken="false"
weather="hot"
for i in {1..5}
do
    json="{\"busId\": $busId, \"fuel\": $fuel, \"seats\": $seats, \"line\": $line, \"nextStop\": $nextStop, \"nextStopDistance\": $nextStopDistance, \"broken\": \"$broken\", \"weather\": \"$weather\"}"
    echo $json | kafka_2.12-2.1.0/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic nuclear-flying-buses
done