package $package$.http.routes

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import $package$.core.Greets
import org.http4s.{Method, Request, Status}
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits.http4sLiteralsSyntax
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class GreetRoutesSpec extends AsyncFreeSpec with AsyncIOSpec with Matchers with Http4sDsl[IO] {

  private val expectedGreeting = "Hello Vasia!"

  private val greetsMock = new Greets[IO] {
    override def greet(name: String): IO[String] = IO.pure(expectedGreeting)
  }

  private val greetRoutes = GreetRoutes[IO](greetsMock).routes

  "GreetRoutes" - {
    "should return a greeting correctly" in {
      for {
        response <- greetRoutes.orNotFound.run(
          Request(method = Method.GET, uri = uri"/greet?name=Name")
        )
        retrieved <- response.as[String]
      } yield {
        response.status shouldBe Status.Ok
        retrieved shouldBe expectedGreeting
      }
    }
  }
}
