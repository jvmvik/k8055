package io.milkyway.k8055;

import groovy.util.logging.Log4j;
import io.milkyway.framework.exception.RouterException;
import io.milkyway.framework.json.gson.GsonFail;
import io.milkyway.framework.json.gson.GsonOk;
import io.milkyway.framework.json.smart.JsonOk;
import io.milkyway.framework.utils.Config;
import io.milkyway.netty.console.WebBrowser;
import io.milkyway.netty.core.Server;
import io.milkyway.netty.exception.RestException;
import io.milkyway.netty.exception.SessionException;
import io.milkyway.netty.webserver.RestRequestHandler;
import io.milkyway.netty.webserver.ViewRequestHandler;
import io.milkyway.netty.webserver.WebServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vicben01
 */
public class App extends Server
{
  static WebServer server;
  static AppController controller;

  Logger log = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws InterruptedException
  {
    BasicConfigurator.configure();

    App app = new App();
    app.start();
    //WebBrowser.start();
  }

  @Override
  public void start() throws InterruptedException
  {
    server = new WebServer();

    // Local instance of config
    Config c = Config.getOrCreate();
    c.setPort(8080);
    c.set(Config.WEB_APP, "web/");
    //c.enableTemplate();

    controller = new AppController();

    route();

    server.start();

    log.info("Application started..");
  }

  void route()
  {
    try
    {
      server.addRequestHandler("/", new ViewRequestHandler()
      {
        @Override
        public void handle(ChannelHandlerContext ctx, HttpRequest req, Map<String, String> params) throws SessionException, RestException
        {
          render(ctx, "index", new HashMap<String, Object>());
        }
      });

      server.addRequestHandler("/isConnected", new RestRequestHandler()
      {
        @Override
        public void handle(ChannelHandlerContext ctx, HttpRequest request, Map<String, String> params) throws SessionException, RestException
        {
          JsonOk ok = new JsonOk();
          ok.put("isConnected", controller.isConnected());
          render(ctx, ok.toJSONString(), HttpResponseStatus.OK);
        }
      });

      server.addRequestHandler("/connect", new RestRequestHandler()
      {
        @Override
        public void handle(ChannelHandlerContext ctx, HttpRequest req, Map<String, String> params) throws SessionException, RestException
        {
          if (controller.connect())
          {
            render(ctx, new GsonOk().toJson(), HttpResponseStatus.OK);
          }
          else
          {
            GsonFail fail = new GsonFail("Connection failed");
            render(ctx, fail.toJson(), HttpResponseStatus.OK);
          }
        }
      });

      server.addRequestHandler("/disconnect", new RestRequestHandler()
      {
        @Override
        public void handle(ChannelHandlerContext ctx, HttpRequest req, Map<String, String> params) throws SessionException, RestException
        {
          if (controller.disconnect())
          {
            render(ctx, new GsonOk().toJson(), HttpResponseStatus.OK);
          }
          else
          {
            GsonFail fail = new GsonFail("Cannot close the connection failed");
            render(ctx, fail.toJson(), HttpResponseStatus.OK);
          }
        }
      });

      server.addRequestHandler("/apply", new RestRequestHandler()
      {
        @Override
        public void handle(ChannelHandlerContext ctx, HttpRequest req, Map<String, String> params) throws SessionException, RestException
        {
          if (controller.apply(params.get("q")))
          {
            render(ctx, new GsonOk().toJson(), HttpResponseStatus.OK);
          }
          else
          {
            render(ctx, new GsonFail("Oups apply failed...").toJson(), HttpResponseStatus.NOT_FOUND);
          }
        }
      });

      server.addRequestHandler("/read", new RestRequestHandler()
      {
        @Override
        public void handle(ChannelHandlerContext ctx, HttpRequest req, Map<String, String> params) throws SessionException, RestException
        {
          render(ctx, controller.read().toString(), HttpResponseStatus.OK);
        }
      });
    }
    catch (RouterException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void stop()
  {
     server.stop();
  }
}
