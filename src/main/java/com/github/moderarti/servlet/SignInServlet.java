package com.github.moderarti.servlet;

import com.github.moderarti.accounts.AccountService;
import com.github.moderarti.accounts.UserProfile;;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        UserProfile user;
        if ((user = accountService.getUserByLogin(login)) != null) {
            if (user.getPassword().equals(password)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println("Authorized: " + login);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().println("Unauthorized");
        }
    }

}
