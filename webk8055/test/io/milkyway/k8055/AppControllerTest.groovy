package io.milkyway.k8055

import com.google.gson.JsonObject
import org.junit.Before
import org.junit.Test

/**
 * @author vicben01
 */
class AppControllerTest
{
  AppController controller

  @Before
  void setUp()
  {
    controller = new AppController()
  }

  @Test
  void apply()
  {
    String q =
"""
{
"direction":"output",
"digital":[
  {"x":1,"on":true},
  {"x":2,"on":false},
  {"x":3,"on":false},
  {"x":4,"on":false},
  {"x":5,"on":false}
],
"analog":
{
 "da1":0,
 "da2":100
}
}
"""
    assert controller.apply(q)

  }

  @Test
  void read()
  {
    // MOck JNA implementation
    JsonObject j = controller.read()
    println j.toString()
    assert j

  }
}
