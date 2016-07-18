import java.util
import java.util.Properties
import java.util.concurrent.Executors

import kafka.consumer.ConsumerConfig
import scala.collection.JavaConversions._

object SampleKafkaConsumer extends App {

  val props = new Properties()
  props.put("zookeeper.connect", "sandbox.hortonworks.com:2181")
  props.put("group.id", "1")
  props.put("zookeeper.session.timeout.ms", "400")
  props.put("zookeeper.sync.time.ms", "200")
  props.put("auto.commit.interval.ms", "1000")
  val cfg = new ConsumerConfig(props)

  val consumer = kafka.consumer.Consumer.createJavaConsumerConnector(cfg)

  val topic = "kafkatest"

  val topicCountMap = new util.HashMap[String, Integer]()
  topicCountMap.put(topic, new Integer(1))

  val consumerMap = consumer.createMessageStreams(topicCountMap)
  val streams = consumerMap.get(topic).toList

  // now launch all the threads
  //
  val executor = Executors.newFixedThreadPool(1)

  // now create an object to consume the messages
  var threadNumber = 0
  for (stream <- streams) {
    println("Build new executor")
    executor.submit(new ConsumerTest(stream, threadNumber))
    threadNumber+=1
  }
}