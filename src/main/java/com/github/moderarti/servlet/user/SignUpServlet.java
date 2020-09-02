package com.github.moderarti.servlet.user;

import com.github.moderarti.models.accounts.AccountService;
import com.github.moderarti.models.accounts.UserProfile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {

    private AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (accountService.getUserByLogin(login) == null && password != null) {
            UserProfile newUser = new UserProfile(login, password);
            accountService.addNewUser(newUser);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return;

    }

}
