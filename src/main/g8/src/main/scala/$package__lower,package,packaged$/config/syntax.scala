package $package$.config

import cats.MonadThrow
import cats.implicits._
import pureconfig.error.ConfigReaderException
import pureconfig.{ConfigReader, ConfigSource}

import scala.reflect.ClassTag

object syntax {

  implicit class ConfigSourceWrapper(source: ConfigSource) {
    def loadF[F[_], A](implicit reader: ConfigReader[A], F: MonadThrow[F], tag: ClassTag[A]): F[A] =
      F.pure(source.load[A]).flatMap {
        case Left(errors)  => F.raiseError[A](ConfigReaderException(errors))
        case Right(config) => F.pure(config)
      }
  }
}
