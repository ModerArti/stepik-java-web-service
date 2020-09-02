package com.github.moderarti;

import com.github.moderarti.accounts.AccountService;
import com.github.moderarti.accounts.AccountServiceMBean;
import com.github.moderarti.property.PropertiesENUM;
import com.github.moderarti.property.PropertiesHandler;
import com.github.moderarti.servlet.admin.AdminServlet;
import com.github.moderarti.servlet.chat.WebSocketChatServlet;
import com.github.moderarti.servlet.user.SignInServlet;
import com.github.moderarti.servlet.user.SignUpServlet;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.http.HttpServlet;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.toString());

    private static Map<HttpServlet, String> getServletsAndPages(AccountService accountService) {
        Map<HttpServlet, String> servlets = new HashMap<>();
        servlets.put(new SignUpServlet(accountService), "/signup");
        servlets.put(new SignInServlet(accountService), "/signin");
        servlets.put(new WebSocketChatServlet(), "/chat");
        servlets.put(new AdminServlet(accountService), "/admin");
        return servlets;
    }

    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");

        AccountService accountService = new AccountService();
        AccountServiceMBean serverStatistics = accountService;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=AccountService.usersLimit");
        mbs.registerMBean(serverStatistics, name);

        Map<HttpServlet, String> servletsAndPages = getServletsAndPages(accountService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        for (Map.Entry<HttpServlet, String> servletAndPage : servletsAndPages.entrySet()) {
            context.addServlet(new ServletHolder(servletAndPage.getKey()), servletAndPage.getValue());
        }
        int numberOfPort = Integer.parseInt(PropertiesHandler.getProperty(PropertiesENUM.SERVER_PORT));
        Server server = new Server(numberOfPort);
        server.setHandler(context);

        server.start();
        LOGGER.info("Server started");
        server.join();
    }

}