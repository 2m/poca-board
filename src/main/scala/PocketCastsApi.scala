package lt.dvim.pocaboard

import derive.key

object PocketCastsApi {
  case class FormData(name: String, value: String)
  case class LoginBody(status: String)

  case class LoginRequest(url: String, form: Seq[FormData])
  case class LoginResponse(body: LoginBody, cookies: Seq[(String, String)])

  case class Podcast(author: String, title: String, description: String, url: String, @key("thumbnail_url")thumbnailUrl: String)
  case class SubscriptionsBody(podcasts: Seq[Podcast])

  case class SubscriptionsRequest(url: String, cookie: Seq[String])
  case class SubscriptionsResponse(body: SubscriptionsBody)
}
