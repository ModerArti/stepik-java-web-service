package com.github.moderarti;

import com.github.moderarti.models.accounts.AccountService;
import com.github.moderarti.models.accounts.AccountServiceMBean;
import com.github.moderarti.models.resources.ResourceService;
import com.github.moderarti.models.resources.ResourceServiceMBean;
import com.github.moderarti.property.PropertiesENUM;
import com.github.moderarti.property.PropertiesHandler;
import com.github.moderarti.servlet.admin.AdminServlet;
import com.github.moderarti.servlet.chat.WebSocketChatServlet;
import com.github.moderarti.servlet.resource.ResourceServlet;
import com.github.moderarti.servlet.user.SignInServlet;
import com.github.moderarti.servlet.user.SignUpServlet;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import javax.servlet.http.HttpServlet;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.toString());

    private static Map<HttpServlet, String> getServletsAndPages(AccountService accountService,
                                                                ResourceService resouceService) {
        Map<HttpServlet, String> servlets = new HashMap<>();
        servlets.put(new SignUpServlet(accountService), "/signup");
        servlets.put(new SignInServlet(accountService), "/signin");
        servlets.put(new WebSocketChatServlet(), "/chat");
        servlets.put(new AdminServlet(accountService), "/admin");
        servlets.put(new ResourceServlet(resouceService), "/com/github/moderarti/models/resources");
        return servlets;
    }

    private static void configureBeans(AccountServiceMBean accountService, ResourceServiceMBean resourceService) throws Exception {

        AccountServiceMBean serverStatistics = accountService;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=AccountService.usersLimit");
        mbs.registerMBean(serverStatistics, name);


        ObjectName resourceServiceName = new ObjectName("Admin:type=ResourceServerController");
        StandardMBean resourceServiceMBean = new StandardMBean(resourceService, ResourceServiceMBean.class);
        mbs.registerMBean(resourceServiceMBean, resourceServiceName);
    }

    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure("src/main/com.github.moderarti.models.resources/log4j.properties");

        AccountService accountService = new AccountService();
        ResourceService resourceService = new ResourceService();

        configureBeans(accountService, resourceService);

        Map<HttpServlet, String> servletsAndPages = getServletsAndPages(accountService, resourceService);

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