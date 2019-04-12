# FunctionalDataProgrammingPrj

## Use case

We chose to create a big data architecture for a fleet of nuclear flying buses, a brand new transportation system that will obviously become the safest one.
Even though our buses can fly above regular traffic, they are organized in lines and stops like regular buses.
The buses regularly send data to the big data datacenter to tell their status, this data includes:

* Remaining fuel (uranium in milligrams)
* Total seats in the bus
* Passengers currently in the bus
* Line number
* Next stop ID
* Distance to the next stop (meters)
* Total kilometers travelled
* Whereas the bus is broken or not
* The weather (hot/cold)
* Country (name, North or South emisphere)


## Lauching the project

### Kafka

Download the repository, open a terminal in it, and type the following commands to run Kafka:

Start zookeeper:

```bash
kafka_2.12-2.1.0/bin/zookeeper-server-start.sh kafka_2.12-2.1.0/config/zookeeper.properties
```

In a new terminal, start server:

```bash
kafka_2.12-2.1.0/bin/kafka-server-start.sh kafka_2.12-2.1.0/config/server.properties
```

In a new terminal, create the topic:

```bash
kafka_2.12-2.1.0/bin/kafka-topics.sh --create --topic nuclear-flying-buses --zookeeper localhost:2181 --partitions 1 --replication-factor 1
```

Start Json file writer:

```bash
kafka_2.12-2.1.0/bin/connect-standalone.sh kafka_2.12-2.1.0/config/connect-standalone.properties kafka_2.12-2.1.0/config/connect-console-sink.properties
```

#### Real time monitoring with Scala

Open the **NuclearBusesKafka** Scala project in IntelliJ and run the **MyConsumer** class. The Scala project now listens for messages in the *nuclear-flying-buses* topic.

Back to the terminal run the producer script to write some random data in the topic:

```bash
./produce-messages.sh
```

When a bus breaks, a message is displayed in the IntelliJ console.


### Spark

Open the **Spark** Scala project in IntelliJ and run the **AnalyzeData** class. Bus data is analyzed and results are displayed in the IntelliJ console.


## Architecture

1. The bash script produces messages to the *nuclear-flying-buses* topic (buses move, passengers board and get off, weather changes, buses break...).
    * A Kafka Consumer in Scala consumes the topic messages and analyzes buses' status.
    * Messages are also written in the *nuclear-flying-buses.json* file.
2. A separately run Spark project reads the *nuclear-flying-buses.json* file to analyze its data.