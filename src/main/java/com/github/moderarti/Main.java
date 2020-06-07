package com.github.moderarti;

import com.github.moderarti.property.PropertiesENUM;
import com.github.moderarti.property.PropertiesHandler;
import com.github.moderarti.servlet.KeyValueServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws Exception {
        KeyValueServlet servlet = new KeyValueServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(servlet), "/mirror");

        int numberOfPort = Integer.parseInt(PropertiesHandler.getProperty(PropertiesENUM.PORT));
        Server server = new Server(numberOfPort);
        server.setHandler(context);

        server.start();
        Logger.getGlobal().info("Server started");
        server.join();
    }

}