package ru.job4j.hql.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.hql.model.Order;
import java.util.Collection;
import java.util.Optional;

public class OrderStore {
    private final SessionFactory sessionFactory;

    public OrderStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Order save(Order order) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(order);
        session.getTransaction().commit();
        session.close();
        return order;
    }

    public Collection<Order> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Order> query = session.createQuery("from Order", Order.class);
        Collection<Order> orders = query.list();
        session.getTransaction().commit();
        session.close();
        return orders;
    }

    public Order findById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Order> query = session.createQuery("from Order o where o.id = :id", Order.class);
        query.setParameter("id", id);
        Order order = query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return order;
    }

    public void update(Order order) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("update Order set name = :name, description = :description where id = :id")
                .setParameter("name", order.getName())
                .setParameter("description", order.getDescription())
                .setParameter("id", order.getId())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public Optional<Order> findByName(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Order> query = session.createQuery("from Order o where o.name = :name", Order.class);
        query.setParameter("name", name);
        Order order = query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(order);
    }
}
