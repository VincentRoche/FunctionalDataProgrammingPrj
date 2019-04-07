#!/bin/bash

busIds=(1 2 3)
fuel=(100 100 100)
seats=50
lines=(1 2 1)
nextStops=(1 1 5)
nextStopDistances=(1000 500 850)
totalKms=(250000 10000 400000)
broken=("false" "false" "false")
weathers=("hot" "cold" "hot")
countries=("France" "Australia" "France")
northHemisphere=(true false true)

for i in {1..5}
do
    # Updating all the buses
    for i in ${!busIds[*]}
    do
        busId=${busIds[i]}
        newFuel=${fuel[i]}
        line=${lines[i]}
        newNextStop=${nextStops[i]}
        newNextStopDist=${nextStopDistances[i]}
        newTotalKms=${totalKms[i]}
        newBroken=${broken[i]}
        newWeather=${weathers[i]}
        country=${countries[i]}
        isNorthHemisphere=${northHemisphere[i]}

        # Randomly breaks
        if [[ $newBroken = false  && ( $newFuel -le 0 || $(( RANDOM % 10 )) -eq 0 ) ]]
        then
            newBroken=true
        fi

        if [ $newBroken = false ]
        then
            moveDistance=$(( 200 + RANDOM % 50 ))
            usedFuel=$(( $moveDistance * 0,1))

            newNextStopDist=$(( $newNextStopDist - $moveDistance ))
            # If the bus arrived at the stop, go to next stop
            if [ $newNextStopDist -le 0 ]
            then
                newNextStopDist=1000
                newNextStop=$(( $newNextStop + 1 ))
            fi
            newTotalKms=$(( $newTotalKms + $moveDistance ))
            newFuel=$(( $newFuel - $usedFuel ))
        fi

        json="{\"busId\": $busId, \"fuel\": $newFuel, \"seats\": $seats, \"line\": $line, \"nextStop\": $newNextStop, \"nextStopDistance\": $newNextStopDist, \"totalKms\": $newTotalKms, \"broken\": $newBroken, \"weather\": \"$newWeather\", \"country\": { \"name\": \"$country\", \"northHemisphere\": $isNorthHemisphere }}"
        echo $json | kafka_2.12-2.1.0/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic nuclear-flying-buses

        # Saving values
        fuel[i]=$newFuel
        nextStops[i]=$newNextStop
        nextStopDistances[i]=$newNextStopDist
        totalKms[i]=$newTotalKms
        broken[i]=$newBroken
        weathers[i]=$newWeather
    done
done