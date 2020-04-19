package com.evolutiongaming.vacations.dashboard

import java.time.LocalDateTime
import java.util.{Timer, TimerTask}

import cats.effect.IO
import fs2.Pipe
import fs2.Stream
import fs2.concurrent.Topic
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.server.websocket.WebSocketBuilder
import org.http4s.websocket.WebSocketFrame

import scala.concurrent.ExecutionContext

object Dashboard {
  implicit val contextShift = IO.contextShift(ExecutionContext.global)
  private val topic: Topic[IO, WebSocketFrame] = Topic[IO, WebSocketFrame](WebSocketFrame.Text("")).unsafeRunSync()

  private val toClient: Stream[IO, WebSocketFrame] = topic.subscribe(10)
  private val fromClient: Pipe[IO, WebSocketFrame, Unit] = _.map(msg ⇒ update(msg))

  def routes = HttpRoutes.of[IO] {
    case GET -> Root / "dashboard" / "update" =>
      WebSocketBuilder[IO].build(toClient, fromClient)
  }

  def update[A](message: A): Unit = {
    topic.publish1(WebSocketFrame.Text(message.toString)).unsafeRunSync()
  }

  //TODO: remove
  private val t = new Timer()
  t.scheduleAtFixedRate(new TimerTask {
    override def run(): Unit = update(s"test message  - ${LocalDateTime.now()}")
  }, 5000, 5000)
}
