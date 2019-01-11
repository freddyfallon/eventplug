package com.freddyinc.eventplug

import cats.effect.IO
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Await
import io.finch._
import io.finch.catsEffect._
import io.finch.circe._
import io.circe.generic.auto._

object Main extends App {

  case class Message(goodboys: String)

  def healthcheck: Endpoint[IO, String] = get(pathEmpty) {
    Ok("OK")
  }

  def plugWithTopic: Endpoint[IO, Message] = get(path[String] :: "plug" ) { topic: String =>
    Ok(Message(topic))
  }

  def plug: Endpoint[IO, Message] = get("plug") {
    Ok(Message("Welcome to eventplug"))
  }

  def service: Service[Request, Response] = Bootstrap
    .serve[Text.Plain](healthcheck)
    .serve[Application.Json](plugWithTopic :+: plug)
    .toService

  Await.ready(Http.server.serve(":8081", service))
}