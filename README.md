# FunctionalDataProgrammingPrj

## Flying bus


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
kafka_2.12-2.1.0/bin/kafka-topics.sh --create --topic t1 --zookeeper localhost:2181 --partitions 3 --replication-factor 1
```

Start producer:

```bash
kafka_2.12-2.1.0/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic t1
```

Start consumer:

```bash
kafka_2.12-2.1.0/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t1 --from-beginning
```
