package com.github.moderarti;

import com.github.moderarti.accounts.AccountService;
import com.github.moderarti.property.PropertiesENUM;
import com.github.moderarti.property.PropertiesHandler;
import com.github.moderarti.servlet.SignInServlet;
import com.github.moderarti.servlet.SignUpServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.toString());

    private static Map<HttpServlet, String> getServletsAndPages(AccountService accountService) {
        Map<HttpServlet, String> servlets = new HashMap<>();
        servlets.put(new SignUpServlet(accountService), "signup");
        servlets.put(new SignInServlet(accountService), "signin");
        return servlets;
    }

    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();
        Map<HttpServlet, String> servletsAndPages = getServletsAndPages(accountService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        for (Map.Entry<HttpServlet, String> servletAndPage : servletsAndPages.entrySet()) {
            context.addServlet(new ServletHolder(servletAndPage.getKey()), servletAndPage.getValue());
        }

        int numberOfPort = Integer.parseInt(PropertiesHandler.getProperty(PropertiesENUM.PORT));
        Server server = new Server(numberOfPort);
        server.setHandler(context);

        server.start();
        LOGGER.info("Server started");
        server.join();
    }

}