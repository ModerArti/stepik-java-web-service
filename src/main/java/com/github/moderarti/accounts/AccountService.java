package com.github.moderarti.accounts;

import com.github.moderarti.database.service.DatabaseService;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private Map<String, UserProfile> loginToProfile = new HashMap<>();
    private Map<String, UserProfile> sessionIdToProfile = new HashMap<>();
    private DatabaseService service = new DatabaseService();

    public AccountService() { }

    public void addNewUser(UserProfile userProfile) {
        service.addUser(userProfile);
    }

    public void addNewSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        return service.getUser(login);
    }

    public UserProfile getUserBySession(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

}
