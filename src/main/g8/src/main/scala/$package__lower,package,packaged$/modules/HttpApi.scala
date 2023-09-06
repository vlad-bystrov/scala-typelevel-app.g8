package $package$.modules

import cats.Monad
import cats.effect.Resource
import cats.implicits._
import $package$.http.routes.{GreetRoutes, HealthRoutes}
import org.http4s.server.Router
import org.http4s.{HttpApp, HttpRoutes}

sealed class HttpApi[F[_]: Monad] private (core: Core[F]) {

  private val healthRoutes = HealthRoutes[F]().routes
  private val greetRoutes  = GreetRoutes[F](core.greets).routes

  private val openRoutes: HttpRoutes[F] = healthRoutes <+> greetRoutes

  private val routes: HttpRoutes[F] = Router(
    "/api" -> openRoutes
  )

  val httpApp: HttpApp[F] = routes.orNotFound
}

object HttpApi {
  def apply[F[_]: Monad](core: Core[F]): Resource[F, HttpApi[F]] =
    Resource.pure(new HttpApi[F](core))
}
