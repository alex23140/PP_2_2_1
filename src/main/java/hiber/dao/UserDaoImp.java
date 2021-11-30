package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserWithCar(String model, int series) {
        Query<Car> query = sessionFactory.getCurrentSession().createQuery(
                "FROM Car car LEFT OUTER Join FETCH car.user where" +
                        " car.model =:model and car.series =:series");
        query.setParameter("model", model);
        query.setParameter("series", series);
        Car car = query.uniqueResult();
        return car.getUser();

    }

}
