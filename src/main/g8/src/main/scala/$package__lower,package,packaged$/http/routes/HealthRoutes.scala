package $package$.http.routes

import cats.Monad
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router

final case class HealthRoutes[F[_]: Monad]() extends Http4sDsl[F] {

  private val prefixPath = "/health"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root =>
    Ok("Server is UP!")
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
