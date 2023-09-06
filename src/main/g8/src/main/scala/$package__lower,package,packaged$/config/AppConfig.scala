package $package$.config

import pureconfig.ConfigReader
import pureconfig.generic.semiauto._

case class AppConfig(emberConfig: EmberConfig)

object AppConfig {

  implicit val appConfigReader: ConfigReader[AppConfig] = deriveReader[AppConfig]
}
