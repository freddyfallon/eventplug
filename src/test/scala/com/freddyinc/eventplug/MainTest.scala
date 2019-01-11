package com.freddyinc.eventplug

import io.finch._
import org.scalatest.FunSuite

class MainTest extends FunSuite {
  test("healthcheck") {
    assert(Main.healthcheck(Input.get("/")).awaitValueUnsafe() == Some("OK"))
  }

  test("plug") {
    assert(Main.plug(Input.get("/plug")).awaitValueUnsafe() == Some(Main.Message("Welcome to eventplug")))
  }

  test("plugWithTopic") {
    assert(Main.plugWithTopic(Input.get("/hello/plug")).awaitValueUnsafe() == Some(Main.Message("hello")))
  }
}