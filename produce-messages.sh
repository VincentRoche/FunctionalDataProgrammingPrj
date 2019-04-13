#!/bin/bash

busIds=(1 2 3 4 5)
fuel=(100 76 100 89 12)
seats=50
passengers=(0 16 39 49 49)
lines=(1 2 1 3 4)
nextStops=(1 1 5 2 9)
nextStopDistances=(1000 500 850 888 10)
totalKms=(250000 10000 400000 3333 4444)
broken=("false" "false" "false" "false" "false")
weathers=("hot" "cold" "hot" "hot" "cold")
countries=("France" "Australia" "France" "Germany" "Argentina")
northHemisphere=(true false true, true false)

for i in {1..30}
do
    # Updating all the buses
    for i in ${!busIds[*]}
    do
        busId=${busIds[i]}
        newFuel=${fuel[i]}
        newPassengers=${passengers[i]}
        line=${lines[i]}
        newNextStop=${nextStops[i]}
        newNextStopDist=${nextStopDistances[i]}
        newTotalKms=${totalKms[i]}
        newBroken=${broken[i]}
        newWeather=${weathers[i]}
        country=${countries[i]}
        isNorthHemisphere=${northHemisphere[i]}

        # Random weather
        if [[ $(( RANDOM % 2 )) -eq 0 ]]
        then
            newWeather="hot"
        else
            newWeather="cold"
        fi

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
            # If the bus arrived at the stop, passengers change, then go to next stop
            if [ $newNextStopDist -le 0 ]
            then
                newPassengers=$(( $newPassengers + RANDOM % 60 - 30 ))
                if [ $newPassengers -lt 0 ]
                then
                    newPassengers=0
                elif [ $newPassengers -gt $seats ]
                then
                    newPassengers=$seats
                fi

                newNextStopDist=1000
                newNextStop=$(( $newNextStop + 1 ))
            fi
            newTotalKms=$(( $newTotalKms + $moveDistance ))
            newFuel=$(( $newFuel - $usedFuel ))
        fi

        json="{\"busId\": $busId, \"fuel\": $newFuel, \"seats\": $seats, \"passengers\": $newPassengers, \"line\": $line, \"nextStop\": $newNextStop, \"nextStopDistance\": $newNextStopDist, \"totalKms\": $newTotalKms, \"broken\": $newBroken, \"weather\": \"$newWeather\", \"country\": { \"name\": \"$country\", \"northHemisphere\": $isNorthHemisphere }}"
        echo $json | kafka_2.12-2.1.0/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic nuclear-flying-buses

        # Saving values
        fuel[i]=$newFuel
        passengers[i]=$newPassengers
        nextStops[i]=$newNextStop
        nextStopDistances[i]=$newNextStopDist
        totalKms[i]=$newTotalKms
        broken[i]=$newBroken
        weathers[i]=$newWeather
    done
done