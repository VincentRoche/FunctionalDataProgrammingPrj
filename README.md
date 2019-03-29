# FunctionalDataProgrammingPrj

Start zookeeper:

```bash
kafka_2.12-2.1.0/bin/zookeeper-server-start.sh kafka_2.12-2.1.0/config/zookeeper.properties
```

Start server:

```bash
kafka_2.12-2.1.0/bin/kafka-server-start.sh
kafka_2.12-2.1.0/config/server.properties
```

Create topic:

```bash
kafka_2.12-2.1.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 2 --topic t1
```

Start producer (not working):

```bash
kafka_2.12-2.1.0/bin/kafka-console-producer.sh --broker-list localhost:2181 --topic t1
```

Start consumer:

```bash
kafka_2.12-2.1.0/
```