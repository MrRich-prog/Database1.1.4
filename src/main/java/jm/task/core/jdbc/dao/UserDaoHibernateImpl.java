package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session s = Util.getSessionFactory().openSession();
        Transaction tx = s.getTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS UserTable (" +
                "`id` INT NOT NULL AUTO_INCREMENT," +
                "`name` VARCHAR(45) NULL," +
                "`lastname` VARCHAR(45) NULL," +
                "`age` INT(100) NULL," +
                "PRIMARY KEY (`id`))";
        tx.begin();
            s.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        tx.commit();
        s.close();
    }

    @Override
    public void dropUsersTable() {
        Session s = Util.getSessionFactory().openSession();
        Transaction tx = s.getTransaction();
        String sql = "DROP TABLE IF EXISTS UserTable ";
        tx.begin();
            s.createSQLQuery(sql).executeUpdate();
        tx.commit();
        s.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session s = Util.getSessionFactory().openSession();
        Transaction tx = s.getTransaction();
        tx.begin();
            User u = new User();
            u.setName(name);
            u.setLastName(lastName);
            u.setAge(age);
            s.save(u);
        tx.commit();
        s.close();
    }

    @Override
    public void removeUserById(long id) {
        Session s = Util.getSessionFactory().openSession();
        Transaction tx = s.getTransaction();
        tx.begin();
            User u = s.get(User.class, id);
            s.delete(u);
        tx.commit();
        s.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session s = Util.getSessionFactory().openSession();
        Transaction tx = s.getTransaction();
        tx.begin();
            List<User> users = (List<User>) s.createQuery("from User", User.class).list();
        tx.commit();
        s.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session s = Util.getSessionFactory().openSession();
        Transaction tx = s.getTransaction();
        tx.begin();
            s.createQuery("delete from User").executeUpdate();
        tx.commit();
        s.close();
    }
}
