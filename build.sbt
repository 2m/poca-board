organization := "lt.dvim"
name := "pocaboard"

libraryDependencies ++= Seq(
  "com.github.japgolly.scalajs-react" %%% "core"           % "0.11.3",
  "com.github.japgolly.scalajs-react" %%% "extra"          % "0.11.3",
  "com.github.japgolly.scalajs-react" %%% "ext-monocle"    % "0.11.3",
  "fr.hmil"                           %%% "roshttp"        % "2.0.1",
  "com.lihaoyi"                       %%% "upickle"        % "0.4.4",
  "com.github.julien-truffaut"        %%%  "monocle-macro" % "1.4.0"
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
  Seq((packageJSDependencies in Compile).value, (fastOptJS in Compile).value.data).foreach { js =>
    IO.copyFile(js, directory / js.getName)
  }
  directory
}

enablePlugins(SbtWeb, ScalaJSPlugin)
moduleName in fastOptJS in Compile := name.value