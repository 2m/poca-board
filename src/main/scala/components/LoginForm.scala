package lt.dvim.pocaboard.components

import fr.hmil.roshttp.HttpRequest
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.ExternalVar
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import lt.dvim.pocaboard.Routes
import lt.dvim.pocaboard.UpickleJsonBody._
import upickle.default._

import scala.concurrent.Future
import scala.util.{Failure, Success}

object LoginForm {
  import lt.dvim.pocaboard.PocketCastsApi._

  final val HttpCorsProxy = "https://us-central1-pocaboard-2m.cloudfunctions.net/corsProxy"

  val login = (email: String, password: String) => CallbackTo[Future[Seq[Podcast]]] {
    import monix.execution.Scheduler.Implicits.global

    HttpRequest(HttpCorsProxy).post(
      LoginRequest("https://play.pocketcasts.com/security/login", Seq(
        FormData("email", email),
        FormData("password", password)
    ))).flatMap { response =>
      val loginResponse = read[LoginResponse](response.body)
      val sessionCookie = loginResponse.cookies.collect {
        case (name, value) if name == "_social_session" => s"$name=$value"
      }
      HttpRequest(HttpCorsProxy).post(SubscriptionsRequest(
        "https://play.pocketcasts.com/web/podcasts/all.json",
        sessionCookie
      ))
    }.map { response =>
      val subscriptionsResponse = read[SubscriptionsResponse](response.body)
      subscriptionsResponse.body.podcasts
    }
  }

  case class Props(router: RouterCtl[Routes.Loc], podcasts: ExternalVar[Seq[Podcast]])
  case class State(email: String, password: String, podcasts: Seq[Podcast])

  class Backend($: BackendScope[Props, State]) {

    def onEmailChange(e: ReactEventI) =
      e.extract(_.target.value) { value =>
        $.modState(_.copy(email = value))
      }

    def onPasswordChange(e: ReactEventI) =
      e.extract(_.target.value) { value =>
        $.modState(_.copy(password = value))
      }

    val retrieveSubsriptions = $.props.flatMap { p =>
      $.state.flatMap { s =>
        import scala.concurrent.ExecutionContext.Implicits.global
        login(s.email, s.password).map {
          _.onComplete {
            case Success(podcasts) => p.podcasts.set(podcasts)
            case Failure(ex) => Callback.log("Error while getting podcasts")
          }
        }
      }
    }

    val goToSubscriptions = $.props.flatMap { p =>
      $.state.flatMap { s =>
        p.router.set(Routes.Subscriptions(s.email))
      }
    }

    def handleSubmit(e: ReactEventI) =
      e.preventDefaultCB >> Callback.log("The button was pressed!") >> retrieveSubsriptions >> goToSubscriptions

    def render(state: State) =
      <.div(
        ^.cls := "demo-blog__posts mdl-grid",
        <.div(
          ^.cls := s"mdl-card mdl-cell mdl-cell--12-col",
          <.div(
            ^.cls := "demo-card-fullwide mdl-card mdl-shadow--2dp",
            <.div(
              ^.cls := "baseball mdl-card__title",
              <.h3(
                ^.cls := "mdl-card__title-text mdl-color-text--white",
                "Share Your Pocket Casts Subscriptions"
              )
            ),
            <.div(
              ^.cls := "mdl-card__supporting-text",
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
              <.div(
                ^.cls := "mdl-fieltooltip",
                <.div(
                  ^.id := "password_tooltip",
                  ^.cls := "icon material-icons",
                  "lock"
                ),
                <.div(
                  ^.cls := "mdl-tooltip",
                  ^.`for` := "password_tooltip",
                  "Your Pocket Casts password is not going to be stored. It is going to be sent over the secure connection to Pocket Casts servers to retrieve your podcast subscriptions."
                )
              ),
              <.button(
                ^.cls := "mdl-button mdl-button--fab mdl-js-button mdl-js-ripple-effect mdl-color--accent mdl-color-text--white",
                ^.onClick ==> handleSubmit,
                <.i(
                  ^.cls := "material-icons",
                  "add"
                )
              )
            )
          )
        )
      )
  }

  val component =
    ReactComponentB[Props]("Login")
      .initialState(State("", "", Seq.empty))
      .renderBackend[Backend]
      .build

  def apply(router: RouterCtl[Routes.Loc], podcasts: ExternalVar[Seq[Podcast]]) = component(Props(router, podcasts))
}
