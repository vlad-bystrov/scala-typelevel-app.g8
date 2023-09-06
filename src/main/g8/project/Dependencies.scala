import sbt._

object Dependencies {
  // versions
  lazy val catsEffectVersion = "3.5.0"
  lazy val doobieVersion     = "1.0.0-RC4"
  lazy val circeVersion      = "0.14.5"
  lazy val http4sVersion     = "0.23.23"
  lazy val pureConfigVersion = "0.17.4"

  lazy val log4catsVersion = "2.5.0"
  lazy val logbackVersion  = "1.4.7"
  lazy val slf4jVersion    = "2.0.5"

  lazy val scalaTestVersion           = "3.2.16"
  lazy val scalaTestCatsEffectVersion = "1.4.0"
  lazy val testContainerVersion       = "1.17.6"

  // libraries
  val catsEffect = "org.typelevel"         %% "cats-effect" % catsEffectVersion
  val pureconfig = "com.github.pureconfig" %% "pureconfig"  % pureConfigVersion

  val doobie = Seq(
    "org.tpolecat" %% "doobie-core"      % doobieVersion,
    "org.tpolecat" %% "doobie-hikari"    % doobieVersion,
    "org.tpolecat" %% "doobie-postgres"  % doobieVersion,
    "org.tpolecat" %% "doobie-scalatest" % doobieVersion % Test
  )

  val circe = Seq(
    "io.circe" %% "circe-core"    % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-parser"  % circeVersion
  )

  val http4s = Seq(
    "org.http4s" %% "http4s-dsl"          % http4sVersion,
    "org.http4s" %% "http4s-ember-server" % http4sVersion,
    "org.http4s" %% "http4s-circe"        % http4sVersion
  )

  val logging = Seq(
    "org.typelevel" %% "log4cats-slf4j"  % log4catsVersion,
    "org.slf4j"      % "slf4j-simple"    % slf4jVersion,
    "org.typelevel" %% "log4cats-noop"   % log4catsVersion % Test,
    "ch.qos.logback" % "logback-classic" % logbackVersion  % Test
  )

  val testing = Seq(
    "org.scalatest" %% "scalatest"                     % scalaTestVersion           % Test,
    "org.typelevel" %% "cats-effect-testing-scalatest" % scalaTestCatsEffectVersion % Test
  )

  // projects
  val common = Seq(catsEffect, pureconfig)

  val backendDeps = common ++ doobie ++ circe ++ http4s ++ logging ++ testing
}
