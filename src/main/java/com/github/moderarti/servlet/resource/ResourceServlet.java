package com.github.moderarti.servlet.resource;

import com.github.moderarti.models.resources.ResourceService;
import com.github.moderarti.models.resources.TestResource;
import com.github.moderarti.parsing.sax.ReadXMLFileSAX;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourceServlet extends HttpServlet {

    private final ResourceService resourceService;

    public ResourceServlet(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getParameter("path");
        resp.setContentType("text/html;charset=utf-8");

        if (path == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        TestResource resource = (TestResource) ReadXMLFileSAX.readXML(path);
        resourceService.setResource(resource);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}