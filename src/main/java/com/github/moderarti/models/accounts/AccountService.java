package com.github.moderarti.models.accounts;

import com.github.moderarti.database.service.DatabaseService;

import java.util.HashMap;
import java.util.Map;

public class AccountService implements AccountServiceMBean {

    private int usersLimit = 10;
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

    public int getUsersLimit() {
        return usersLimit;
    }

    public void setUsersLimit(int usersLimit) {
        this.usersLimit = usersLimit;
    }
}
