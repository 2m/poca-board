package lt._2m.pocaboard

import scala.scalajs.js.JSApp
import org.scalajs.dom.ext.Ajax
import scala.util.Success

import scala.concurrent.ExecutionContext.Implicits.global

object PoCaBoard extends JSApp {
  def main(): Unit = {
    println("Hello worlds!")

    val fut = Ajax.post("https://play.pocketcasts.com/security/login",
                        "email=mail&password=pass")
    fut.onComplete {
      case Success(resp) => println(resp.responseText)
      case other         => println("other")
    }
  }
}
