package lt.dvim.pocaboard

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.ExternalVar
import japgolly.scalajs.react.MonocleReact._
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.prefix_<^._
import lt.dvim.pocaboard.PocketCastsApi.Podcast
import org.scalajs.dom
import monocle.macros._

import scala.scalajs.js.JSApp

object Routes {
  sealed trait Loc
  case object Login extends Loc
  case class Subscriptions(email: String) extends Loc
}

object PoCaBoard extends JSApp {

  def layout(c: RouterCtl[Routes.Loc], r: Resolution[Routes.Loc]) = {
    <.div(^.className := "demo-blog mdl-layout mdl-js-layout has-drawer is-upgraded",
      <.main(^.className := "mdl-layout__content", r.render()),
      <.a(
        ^.id := "view-source",
        ^.cls := "mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-color--accent mdl-color-text--white",
        ^.href := "https://github.com/2m/pocaboard",
        ^.target := "_blank",
        "View Source"
      )
    )
  }

  case class State(podcasts: Seq[Podcast])

  val Main = ReactComponentB[Unit]("Pocast Casts Board")
    .initialState(State(Seq.empty[Podcast]))
    .render { $ =>

      val podcasts = ExternalVar.state($.zoomL(GenLens[State](_.podcasts)))

      import Routes._
      val routerConfig = RouterConfigDsl[Loc].buildConfig { dsl =>
        import dsl._
        (
          staticRoute(root, Login) ~> renderR(router => components.LoginForm(router, podcasts)) |
            dynamicRouteCT("#subscriptions" / remainingPath.caseClass[Subscriptions]) ~> dynRender(s => components.Subscriptions(s.email))
          ).notFound(redirectToPage(Login)(Redirect.Replace))
      }.renderWith(PoCaBoard.layout)

      Router(BaseUrl.until_#, routerConfig)()
    }
    .build

  def main(): Unit = {
    ReactDOM.render(Main(), dom.document.getElementById("root"))
  }
}
