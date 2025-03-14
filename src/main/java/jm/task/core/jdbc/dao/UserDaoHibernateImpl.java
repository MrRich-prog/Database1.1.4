package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session s = Util.getSessionFactory().openSession()) {
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
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session s = Util.getSessionFactory().openSession()) {
            Transaction tx = s.getTransaction();
            String sql = "DROP TABLE IF EXISTS UserTable ";
            tx.begin();
            s.createSQLQuery(sql).executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try (Session s = Util.getSessionFactory().openSession()) {
            tx = s.getTransaction();
            tx.begin();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            s.save(user);
            tx.commit();
            System.out.println("Пользователь - " + name + " " + lastName + " " + age + " - добавлен");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HibernateException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session s = Util.getSessionFactory().openSession()) {
            tx = s.getTransaction();
            tx.begin();
            User user = s.get(User.class, id);
            if (user != null) {
                s.delete(user);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HibernateException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session s = Util.getSessionFactory().openSession()) {
            Transaction tx = s.getTransaction();
            tx.begin();
            List<User> users = s.createQuery("from User", User.class).list();
            tx.commit();
            return users;
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try (Session s = Util.getSessionFactory().openSession()) {
            tx = s.getTransaction();
            tx.begin();
            s.createQuery("delete from User").executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new HibernateException(e);
        }
    }
}
