package com.github.moderarti.servlet;

import com.github.moderarti.accounts.AccountService;;

import javax.servlet.http.HttpServlet;

public class SignInServlet extends HttpServlet {

    private AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

}
