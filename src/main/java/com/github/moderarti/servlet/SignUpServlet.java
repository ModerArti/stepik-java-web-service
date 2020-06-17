package com.github.moderarti.servlet;

import com.github.moderarti.accounts.AccountService;

import javax.servlet.http.HttpServlet;

public class SignUpServlet extends HttpServlet {

    private AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

}
