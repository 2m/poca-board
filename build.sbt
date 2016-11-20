organization := "lt._2m.pocaboard"
name := "poca-board"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1"
)

WebKeys.stage := {
  val directory = WebKeys.stage.value
  val js        = (fullOptJS in Compile).value.data
  IO.copyFile(js, directory / js.getName)
  directory
}

scalafmtConfig in ThisBuild := Some(file(".scalafmt"))
reformatOnCompileSettings

enablePlugins(SbtWeb, ScalaJSPlugin)
