package io.milkyway.k8055;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Application controller
 *
 * @author vicben01
 */
public class AppController
{
  Gson gson;
  boolean connected;
  Logger log = LoggerFactory.getLogger(AppController.class);
  HashMap<Object, Object> m;

  public AppController()
  {
    this.gson = new Gson();
    this.connected  = false;

    m = new HashMap<Object, Object>();
    m.put(1, true);
    m.put(2, false);
    m.put(3, false);
    m.put(4, false);
    m.put(5, false);
    m.put(6, false);
    m.put(7, false);
    m.put(8, true);

    m.put("ad1", 10);
    m.put("ad2", 100);


    //TODO Register connection to driver
  }

  public boolean disconnect()
  {
    //TODO Close connection
    connected = false;
    log.info("Disconnecting card...");
    return true;
  }

  public boolean connect()
  {
    //TODO Connect to the card
    connected = true;
    log.info("Connecting card...");
    return true;
  }


  /***
   * Apply user provided values to the card output.
   *
   * Example:
   * {
   * action:'output',
   * digital:[{x:1,on:true},{x:2,on:false}...],
   * analog:
   *  {
   *    ad1:0,
   *    ad2:100
   *  }
   * }
   *
   * @param q set analog and digital output in the card
   * @return true if success
   */
  public boolean apply(String q)
  {
    //System.out.println(q);
    JsonParser parser = new JsonParser();
    JsonObject j = (JsonObject)parser.parse(q);

    if(!"output".equals(j.get("direction").getAsString()))
      throw new AssertionError("Direction must be set to output.");

    JsonArray digital = j.getAsJsonArray("digital");
    JsonObject o;
    for(int i = 0; i < digital.size(); i++)
    {
      //TODO Write bit
      o = digital.get(i).getAsJsonObject();
      int x = o.get("x").getAsInt();
      boolean on = o.get("on").getAsBoolean();
      log.info("Set digital output: {} to {}", x, on);

      // Temporary map for debug.
      m.put(x, on);
    }

    JsonObject analog = j.getAsJsonObject("analog");
    //TODO Set ad1 and ad2
    int da1 = analog.get("da1").getAsInt();
    log.info("Set analog output: da1 to {}", da1);
    m.put("ad1", da1);

    int da2 = analog.get("da2").getAsInt();
    log.info("Set analog output: da2 to {}", da2);
    m.put("ad2", da2);

    return true;
  }

  /***
   * Read current measures
   * and create a JSON object.
   *
   * Example:
   * {
   *  action:'input',
   *  digital:[{x:1,on:true},{x:2,on:false}],
   *  analog:
   *  {
   *   da1:10,
   *   da2:100
   *  }
   * }
   *
   * @return json that represents the analog and digital values read in inputs.
   */
  public JsonObject read()
  {
    JsonArray digital = new JsonArray();
    JsonObject in;
    for(int i = 1; i < 9; i++)
    {
      in = new JsonObject();
      in.add("x", new JsonPrimitive(i));
      // TODO Must read bit value
      in.add("on", new JsonPrimitive((Boolean)m.get(i)));
      digital.add(in);
    }

    JsonObject analog = new JsonObject();
    // TODO Read da1 and da2
    analog.add("ad1", new JsonPrimitive((Integer)m.get("ad1")));
    analog.add("ad2", new JsonPrimitive((Integer)m.get("ad2")));

    // Build JSON
    JsonObject j = new JsonObject();
    j.add("direction", new JsonPrimitive("input"));
    j.add("digital", digital);
    j.add("analog", analog);

    log.info("Read input: " + j.toString());

    return j;
  }

  /**
   * Check if card is connected to the controller.
   * Single point attached.
   * @return link status
   */
  public Boolean isConnected()
  {
    return new Boolean(connected);
  }

}
