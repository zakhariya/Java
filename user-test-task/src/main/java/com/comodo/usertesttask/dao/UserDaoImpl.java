package com.comodo.usertesttask.dao;

import com.comodo.usertesttask.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long getCount() {
        long count = (long) entityManager.createQuery("SELECT COUNT(id) FROM User").getSingleResult();

        return count;
    }

    @Override
    public List<User> listUsers(int limit, int offset) {

        return (List<User>) entityManager.createQuery("FROM User AS u ORDER BY u.id")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        User uusr = getUserById(user.getUserId());

        uusr.setFirstName(user.getFirstName());
        uusr.setLastName(user.getLastName());
        uusr.setBirthDay(user.getBirthDay());
        uusr.setGender(user.getGender());

        entityManager.flush();
    }

    @Override
    public void deleteUser(long userId) {
        entityManager.remove(getUserById(userId));
    }

    @Override
    public boolean userExists(User user) {
        return ((long) entityManager.createQuery("SELECT COUNT(*) FROM User AS u " +
                "WHERE u.firstName = :name AND u.lastName = :surname AND u.birthDay = :bday")
                .setParameter("name", user.getFirstName())
                .setParameter("surname", user.getLastName())
                .setParameter("bday", user.getBirthDay())
                .getSingleResult() > 0);
    }

}
