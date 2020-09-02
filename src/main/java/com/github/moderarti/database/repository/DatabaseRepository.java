package com.github.moderarti.database.repository;

import com.github.moderarti.models.accounts.UserProfile;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class DatabaseRepository {

    private Session session;

    public DatabaseRepository(Session session) {
        this.session = session;
    }

    public UserProfile getUserByName(String name) {
        Criteria criteria = session.createCriteria(UserProfile.class);
        return (UserProfile) criteria.add(Restrictions.eq("login", name))
                                    .uniqueResult();
    }

    public long saveUser(UserProfile user) {
        return (Long) session.save(user);
    }

}
