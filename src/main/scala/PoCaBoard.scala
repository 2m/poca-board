package lt.dvim.pocaboard

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom

import scala.scalajs.js.JSApp

object PoCaBoard extends JSApp {

  sealed trait Loc
  case object Login extends Loc
  case object Subscriptions extends Loc

  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div(^.className := "demo-blog mdl-layout mdl-js-layout has-drawer is-upgraded",
      <.main(^.className := "mdl-layout__content",
        <.div(^.className := "demo-blog__posts mdl-grid", r.render())
      )
    )
  }

  val routerConfig = RouterConfigDsl[Loc].buildConfig { dsl =>
    import dsl._
    (
      staticRoute(root, Login) ~> renderR(ctl => components.LoginForm()) |
      staticRoute("#subscriptions", Subscriptions) ~> renderR(ctl => ???)
    ).notFound(redirectToPage(Login)(Redirect.Replace))
  }.renderWith(layout)

  def main(): Unit = {
    val router = Router(BaseUrl.until_#, routerConfig)
    ReactDOM.render(router(), dom.document.getElementById("root"))
  }
}
