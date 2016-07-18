# kafka-from-the-outside
Simple project template the shows hooking up Kafka Producer and Consumer from the host machine using Kafka in the HDP Sandbox VM

## Prerequisites
1. Horton Works HDP Sandbox Version 2.4
2. Kafka broker turned on in the sandbox VM
3. VM IP Address added to /etc/hosts file on the host machine as xxx.xxx.xxx.xxx sandbox.hortonworks.com


## Verifying Kafka on the sandbox
1. Access the Sandbox using either an ssh session or direct login
```bash
cd /usr/hdp/current/kafka-broker/bin
# Create a Topic
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

# Produce some messages using the console, enter text followed by a newline, each line is a message
./kafka-console-producer.sh --zookeeper localhost:2181 --topic test

# Ctrl-C to stop the producer
# Read all the messages using the console reader
./kafka-console-consumer.sh --zookeeper sandbox.hortonworks.com:2181 --topic test --from-beginning

#Ctrl-C to stop the consumer
```

## Using this project
After verifying Kafka is running on the sanbox, open 2 terminal windows on the host machine in your local project folder.

```bash
sbt run
```
Will open a run dialog to start either the producer or consumer, in one terminal start the consumer, in the other start the producer. 