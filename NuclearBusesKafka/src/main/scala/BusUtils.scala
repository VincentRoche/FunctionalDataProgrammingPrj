import io.circe.parser
import io.circe.generic.semiauto.deriveDecoder

object BusUtils {
    case class Country (
                       name: String,
                       northHemisphere: Boolean
                       )
    case class Bus (
                       busId : Int,
                       fuel : Int,
                       seats : Int,
                       passengers : Int,
                       line : Int,
                       nextStop : Int,
                       nextStopDistance : Int,
                       totalKms: Int,
                       broken: Boolean,
                       weather : String,
                       country : Country
                     )
  def monitoring(bus:Bus):Unit = {
    if(bus.fuel == 0) println("Bus n°" + bus.busId + " has no Uranium remaining")
    if(bus.broken) println("Bus n°" + bus.busId + " is broken")
    if((bus.passengers/bus.seats) == 1) println("Bus n°" + bus.busId + " is full")
  }

  def parseFromJson(line: String): Unit = {
    val input = line.stripMargin
    implicit val decodeCountry = deriveDecoder[Country]
    implicit val staffDecoder = deriveDecoder[Bus]
    val decodeResult = parser.decode[Bus](input)
    decodeResult match {
      case Right(bus) => monitoring(bus)
      case Left(error) => println(error.getMessage())
    }
  }
}
