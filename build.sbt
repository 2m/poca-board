organization := "lt.dvim"
name := "pocaboard"

libraryDependencies ++= Seq(
  "com.github.japgolly.scalajs-react" %%% "core" % "0.11.3",
  "com.github.japgolly.scalajs-react" %%% "extra" % "0.11.3"
)

jsDependencies ++= Seq(
  "org.webjars.bower" % "react" % "15.3.2"
    /        "react-with-addons.js"
    minified "react-with-addons.min.js"
    commonJSName "React",
  "org.webjars.bower" % "react" % "15.3.2"
    /         "react-dom.js"
    minified  "react-dom.min.js"
    dependsOn "react-with-addons.js"
    commonJSName "ReactDOM",
  "org.webjars.bower" % "react" % "15.3.2"
    /         "react-dom-server.js"
    minified  "react-dom-server.min.js"
    dependsOn "react-dom.js"
    commonJSName "ReactDOMServer"
)

WebKeys.stage := {
  val directory = WebKeys.stage.value
  val deps      = (packageJSDependencies in Compile).value
  val js        = (fullOptJS in Compile).value.data
  IO.copyFile(js, directory / js.getName)
  IO.copyFile(deps, directory / deps.getName)
  directory
}

enablePlugins(SbtWeb, ScalaJSPlugin)
