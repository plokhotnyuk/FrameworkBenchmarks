package example

import com.github.plokhotnyuk.jsoniter_scala.core._
import com.github.plokhotnyuk.jsoniter_scala.macros._
import io.youi.http.content._
import io.youi.net._
import io.youi.server._
import io.youi.server.dsl._

case class Message(message: String)

object Main extends App {
  implicit val codec: JsonValueCodec[Message] = JsonCodecMaker.make[Message](CodecMakerConfig())
  new UndertowServerImplementation(new Server {
    handler(
      filters(
        path"/plaintext" / StringContent("Hello, World!", ContentType.`text/plain`),
        path"/json" / StringContent(writeToString(Message("Hello, World!")), ContentType.`application/json`) // TODO use more efficient writeToArray
      )
    )
  }).start()
}