import java.util.Properties
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}

object SampleKafkaProducer extends App {

  println("Begin Kafka Producer")

  val props = new Properties()
  //set according to your environment
  //in this case the ip of the HDP sandbox VM has been entered into /etc/hosts for simplicity
  props.put("bootstrap.servers", "sandbox.hortonworks.com:6667")
  props.put("metadata.broker.list", "sandbox.hortonworks.com:6667")
  props.put("serializer.class", "kafka.serializer.DefaultEncoder")
  props.put("acks", "all")
  props.put("retries", "0")
  props.put("batch.size", "16384")
  props.put("linger.ms", "1")
  props.put("buffer.memory", "33554432")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")


  //setup a simple string key/message producer
  val producer = new KafkaProducer[String, String](props)

  //Send a keyed message if true, otherwise just send a message
  val useKeyedMessage = true

  //use to get exception details to troubleshoot connectivity issues, use non-keyed messages
  val troubleshoot = false

  //some topic already established with your Kafka instance
  val topic = "kafkatest"

  var i = 0
  for (i <- Range(0, 1000)) {
    println(s"Sending: $i")

    if (useKeyedMessage) {
      producer.send(new ProducerRecord[String, String](topic, s"Key: $i", s"Value: $i"))
    } else {
      if (!troubleshoot) {
        producer.send(new ProducerRecord(topic, s"$i"))
      } else {
        producer.send(new ProducerRecord(topic, "Troubleshooting"), new Callback {
          override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
            if (null != exception)
              println(exception.getMessage())
          }
        })
      }
    }
  }

  producer.flush()
  producer.close()

  println("End Kafka Producer")
}