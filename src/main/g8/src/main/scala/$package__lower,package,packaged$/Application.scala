package $package$

import cats.effect.{IO, IOApp}
import $package$.config.AppConfig
import $package$.config.syntax._
import $package$.modules.{Core, HttpApi}
import org.http4s.ember.server.EmberServerBuilder
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import pureconfig.ConfigSource

object Application extends IOApp.Simple {

  implicit val logger: Logger[IO] = Slf4jLogger.getLogger[IO]

  override def run: IO[Unit] =
    ConfigSource.default.loadF[IO, AppConfig].flatMap { case AppConfig(emberConfig) =>
      Logger[IO].info("Starting Http4s Server...")

      val resource = for {
        core    <- Core[IO]
        httpApi <- HttpApi[IO](core)
        server  <- EmberServerBuilder
          .default[IO]
          .withHost(emberConfig.host)
          .withPort(emberConfig.port)
          .withHttpApp(httpApi.httpApp)
          .build
      } yield server

      resource.use(_ => Logger[IO].info(s"Server started on port \${emberConfig.port}") *> IO.never)
    }
}
