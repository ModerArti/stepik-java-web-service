package com.github.moderarti.accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private Map<String, UserProfile> loginToProfile = new HashMap<>();
    private Map<String, UserProfile> sessionIdToProfile = new HashMap<>();

    public AccountService() { }

    public void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public void addNewSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UserProfile getUserBySession(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

}
