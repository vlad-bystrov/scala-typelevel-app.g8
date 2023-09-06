package $package$.config

import com.comcast.ip4s.{Host, Port}
import pureconfig.ConfigReader
import pureconfig.error.CannotConvert
import pureconfig.generic.semiauto._

case class EmberConfig(host: Host, port: Port)

object EmberConfig {

  implicit val hostReader: ConfigReader[Host] = ConfigReader[String].emap { hostString =>
    Host
      .fromString(hostString)
      .toRight(CannotConvert(hostString, Host.getClass.toString, s"Invalid host value \$hostString"))
  }

  implicit val portReader: ConfigReader[Port] = ConfigReader[Int].emap { portInt =>
    Port
      .fromInt(portInt)
      .toRight(CannotConvert(portInt.toString, Port.getClass.toString, s"Invalid port value \$portInt"))
  }

  implicit val emberConfigReader: ConfigReader[EmberConfig] = deriveReader[EmberConfig]
}
