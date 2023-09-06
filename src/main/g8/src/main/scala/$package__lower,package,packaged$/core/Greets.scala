package $package$.core

import cats.Applicative
import cats.implicits._

trait Greets[F[_]] {
  def greet(name: String): F[String]
}

class LiveGreets[F[_]: Applicative] private extends Greets[F] {
  override def greet(name: String): F[String] =
    s"Hello \$name".pure[F]
}

object LiveGreets {
  def apply[F[_]: Applicative]: F[LiveGreets[F]] = new LiveGreets[F].pure[F]
}
