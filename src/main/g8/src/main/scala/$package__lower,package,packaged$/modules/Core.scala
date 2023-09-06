package $package$.modules

import cats.Applicative
import cats.implicits._
import cats.effect.kernel.Resource
import $package$.core.{Greets, LiveGreets}

sealed class Core[F[_]: Applicative] private (val greets: Greets[F])

object Core {
  def apply[F[_]: Applicative]: Resource[F, Core[F]] = {
    val coreF = for {
      greets <- LiveGreets[F]
    } yield new Core[F](greets)

    Resource.eval(coreF)
  }
}
