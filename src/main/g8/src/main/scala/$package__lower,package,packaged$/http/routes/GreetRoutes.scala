package $package$.http.routes

import cats.Monad
import cats.implicits._
import $package$.core.Greets
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router

final case class GreetRoutes[F[_]: Monad](greets: Greets[F]) extends Http4sDsl[F] {

  object NameQueryParam extends QueryParamDecoderMatcher[String]("name")

  private val prefixPath = "/greet"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root :? NameQueryParam(name) =>
    for {
      greet    <- greets.greet(name)
      response <- Ok(greet)
    } yield response
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
