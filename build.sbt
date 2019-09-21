ThisBuild / scalaVersion := "2.13.1"

lazy val root = (project in file("."))
  .settings(
    clean := Def.taskDyn(clean in application).value,
    assembly := Def.taskDyn(assembly in application).value
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

lazy val packagingSettings = Seq(
  assemblyJarName in assembly := "app.jar",
  mainClass in assembly := Some("dev.zerosum.example.Application")
)

lazy val application = (project in file("modules/application"))
  .aggregate(base, domain)
  .dependsOn(domain)
  .settings(aggregate in assembly := false)
  .settings(packagingSettings: _*)
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
