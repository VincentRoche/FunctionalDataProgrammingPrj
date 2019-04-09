import java.util.Properties
import scala.io.Source
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer


object MyProducer extends App{
  val props: Properties = new Properties()

  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])

  val producer: KafkaProducer[String,String] = new KafkaProducer[String,String](props)
  val lines = Source.fromFile("velib-disponibilite-en-temps-reel.csv")
    .getLines
    .drop(1)
    .foreach{
      line => producer.send(new ProducerRecord[String, String]("KilianLeBg", "myNewKey", line)).get()
    }
  //producer.send(record)
  producer.close()
}
