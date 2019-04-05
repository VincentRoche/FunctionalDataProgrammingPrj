# FunctionalDataProgrammingPrj

## Nuclear flying bus

* Fuel (uranium (mg))
* Available seat
* Line
* Next stop ID
* Distance to next stop (m)
* Broken (true/false)
* Weather (hot/cold)

### Json representation

```json
{
    "fuel": 10,
    "seats": 15,
    "line": 1,
    "nextStop": 3,
    "nextStopDistance": 1000,
    "broken": false,
    "weather": "cold"
}

{"fuel": 10, "seats": 15, "line": 1, "nextStop": 3, "nextStopDistance": 1000, "broken": false, "weather": "cold"}
```


## Kafka

Start zookeeper:

```bash
kafka_2.12-2.1.0/bin/zookeeper-server-start.sh kafka_2.12-2.1.0/config/zookeeper.properties
```

Start server:

```bash
kafka_2.12-2.1.0/bin/kafka-server-start.sh kafka_2.12-2.1.0/config/server.properties
```

Create topic:

```bash
kafka_2.12-2.1.0/bin/kafka-topics.sh --create --topic nuclear-flying-buses --zookeeper localhost:2181 --partitions 1 --replication-factor 1
```

Start producer:

```bash
kafka_2.12-2.1.0/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic nuclear-flying-buses
```

Start Json file writer:

```bash
kafka_2.12-2.1.0/bin/connect-standalone.sh kafka_2.12-2.1.0/config/connect-standalone.properties kafka_2.12-2.1.0/config/connect-console-sink.properties
```

Start consumer (not needed):

```bash
kafka_2.12-2.1.0/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic nuclear-flying-buses --from-beginning
```

Delete topic (not needed):

```bash
kafka_2.12-2.1.0/bin/kafka-topics.sh --delete --zookeeper localhost:2181 --topic nuclear-flying-buses
```
