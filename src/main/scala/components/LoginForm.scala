package lt.dvim.pocaboard.components

import org.scalajs.dom.ext.Ajax
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

import scala.concurrent.Future

object LoginForm {

  final val HttpCorsProxy = "https://us-central1-http-cors-proxy.cloudfunctions.net/corsProxy"

  val login = (email: String, password: String) => CallbackTo[Future[String]] {
    import scala.concurrent.ExecutionContext.Implicits.global
    Ajax.post(HttpCorsProxy).map(_.responseText)
  }

  case class State(email: String, password: String)

  class Backend($: BackendScope[Unit, State]) {

    def onEmailChange(e: ReactEventI) =
      e.extract(_.target.value) { value =>
        $.modState(_.copy(email = value))
      }

    def onPasswordChange(e: ReactEventI) =
      e.extract(_.target.value) { value =>
        $.modState(_.copy(password = value))
      }

    def handleSubmit(e: ReactEventI) =
      e.preventDefaultCB >> Callback.log("The button was pressed!")

    def render(state: State) =
      <.div(
        ^.cls := "mdl-card mdl-cell mdl-cell--4-col",
        <.div(
          ^.cls := "mdl-textfield mdl-js-textfield mdl-textfield--floating-label",
          <.input(
            ^.cls := "mdl-textfield__input",
            ^.tpe := "text",
            ^.id := "email",
            ^.value := state.email,
            ^.onChange ==> onEmailChange
          ),
          <.label(
            ^.cls := "mdl-textfield__label",
            ^.`for` := "email",
            "email address"
          )
        ),
        <.div(
          ^.cls := "mdl-textfield mdl-js-textfield mdl-textfield--floating-label",
          <.input(
            ^.cls := "mdl-textfield__input",
            ^.tpe := "password",
            ^.id := "password",
            ^.value := state.password,
            ^.onChange ==> onPasswordChange
          ),
          <.label(
            ^.cls := "mdl-textfield__label",
            ^.`for` := "password",
            "pocketcasts password"
          )
        ),
        <.button(
          ^.cls := "mdl-button mdl-button--raised mdl-js-button mdl-js-ripple-effect",
          ^.onClick ==> handleSubmit,
          "share your pocketcasts"
        )
      )
  }

  val component =
    ReactComponentB[Unit]("Login")
      .initialState(State("", ""))
      .renderBackend[Backend]
      .build

  def apply() = component()
}
