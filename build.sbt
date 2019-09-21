ThisBuild / scalaVersion := "2.13.1"

lazy val root = (project in file("."))
  .settings(
    clean := Def.taskDyn(clean in application).value,
    stage := Def.taskDyn(stage in application).value
  )

lazy val base = (project in file("modules/base"))
  .settings(
    libraryDependencies ++= {
      val airframeVersion = "19.9.7"

      Seq(
        "org.wvlet.airframe" %% "airframe"  % airframeVersion,
        "org.scalactic"      %% "scalactic" % "3.0.8",
        "org.scalatest"      %% "scalatest" % "3.0.8" % Test,
        "org.scalamock"      %% "scalamock" % "4.4.0" % Test
      )
    }
  )

lazy val domain = (project in file("modules/domain"))
  .dependsOn(base)

lazy val application = (project in file("modules/application"))
  .aggregate(base, domain)
  .dependsOn(domain)
  .enablePlugins(JavaAppPackaging)
  .settings(
    packageName in Docker := "sample",
    version in Docker := "1.0",
    dockerRepository := Some("z3r05um"),
    maintainer in Docker := "zerosum <mail@example.com>",
    dockerExposedPorts := List(8080),
    dockerBaseImage := "adoptopenjdk/openjdk8:ubuntu",
    dockerCmd := Nil,
    daemonUser in Docker := "zerosum",
    mainClass in Compile := Some("dev.zerosum.example.Application")
  )
  .settings(
    libraryDependencies ++= {
      val akkaHttpVersion   = "10.1.9"
      val akkaStreamVersion = "2.5.23"

      Seq(
        "com.typesafe.akka" %% "akka-http"           % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-stream"         % akkaStreamVersion,
        "com.typesafe.akka" %% "akka-http-testkit"   % akkaHttpVersion % Test,
        "com.typesafe.akka" %% "akka-stream-testkit" % akkaStreamVersion % Test
      )
    }
  )
