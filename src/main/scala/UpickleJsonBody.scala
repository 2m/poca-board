package lt.dvim.pocaboard

import java.nio.ByteBuffer

import fr.hmil.roshttp.body.{BodyPart, BulkBodyPart}
import upickle._
import upickle.default._

class UpickleJsonBody[T: Writer] private(value: T) extends BulkBodyPart {
  override def contentType: String = s"application/json; charset=utf-8"

  override def contentData: ByteBuffer = ByteBuffer.wrap(write(value).getBytes("utf-8"))
}

object UpickleJsonBody {
  implicit def valueToBodyPart[T: Writer](value: T): BodyPart = new UpickleJsonBody(value)
}
