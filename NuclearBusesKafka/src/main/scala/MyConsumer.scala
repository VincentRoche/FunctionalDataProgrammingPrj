import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}
import java.util.Properties

import scala.collection.JavaConverters._
import java.time.Duration

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.{Deserializer, Serializer}



object MyConsumer extends App {
  val props = new Properties()
  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
  props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
  props.put(ConsumerConfig.GROUP_ID_CONFIG, "1")
  //props.put(ConsumerConfig.GROUP_ID_CONFIG, )
  val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
  consumer.subscribe(List("nuclear-flying-buses").asJava)
  while(true){
    val records: ConsumerRecords[String, String] = consumer.poll(Duration.ofMillis(1000))
    records.asScala.foreach{
    record => BusUtils.parseFromJson(record.value())
   }
  }
  //consumer.commitSync()
  consumer.close()
  }
