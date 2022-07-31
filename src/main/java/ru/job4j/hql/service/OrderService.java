package ru.job4j.hql.service;

import ru.job4j.hql.model.Order;
import ru.job4j.hql.repository.OrderStore;
import java.util.Collection;
import java.util.Optional;

public class OrderService {
    private final OrderStore store;

    public OrderService(OrderStore store) {
        this.store = store;
    }

    public Order save(Order order) {
        return store.save(order);
    }

    public Collection<Order> findAll() {
        return store.findAll();
    }

    public Order findById(Long id) {
        return store.findById(id);
    }

    public void update(Order order) {
        store.update(order);
    }

    public Optional<Order> findByName(String name) {
        return store.findByName(name);
    }
}
