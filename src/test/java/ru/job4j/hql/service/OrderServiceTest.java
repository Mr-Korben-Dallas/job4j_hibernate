package ru.job4j.hql.service;

import static org.assertj.core.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;
import ru.job4j.hql.model.Order;
import ru.job4j.hql.repository.OrderStore;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

class OrderServiceTest {
    private static SessionFactory sessionFactory = null;
    private static OrderService orderService = null;

    @BeforeAll
    static void setup() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate-test.cfg.xml")
                    .build();
            Metadata metadata = new MetadataSources(standardRegistry)
                    .addAnnotatedClass(Order.class)
                    .getMetadataBuilder()
                    .build();
            sessionFactory = metadata
                    .getSessionFactoryBuilder().build();
            orderService = new OrderService(new OrderStore(sessionFactory));

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Test
    void whenSaveOrderAndFindAllOneRowWithDescription() {
        Order order = new Order("Some order", "Some description", LocalDateTime.now());
        orderService.save(order);
        List<Order> orders = (List<Order>) orderService.findAll();
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getDescription()).isEqualTo("Some description");
        assertThat(orders.get(0).getId()).isEqualTo(1);
    }

    @Test
    void whenSaveTwoOrdersAndFindAllOfTwo() {
        Order order1 = new Order("Some order 1", "Some description 1", LocalDateTime.now());
        Order order2 = new Order("Some order 2", "Some description 2", LocalDateTime.now());
        orderService.save(order1);
        orderService.save(order2);
        List<Order> orders = (List<Order>) orderService.findAll();
        assertThat(orders.size()).isEqualTo(2);
        assertThat(orders.get(0).getDescription()).isEqualTo("Some description 1");
        assertThat(orders.get(0).getId()).isEqualTo(1);
        assertThat(orders.get(1).getDescription()).isEqualTo("Some description 2");
        assertThat(orders.get(1).getId()).isEqualTo(2);
    }

    @Test
    void whenSaveTwoOrdersAndFindFirstById() {
        Order order1 = new Order("Some order 1", "Some description 1", LocalDateTime.now());
        Order order2 = new Order("Some order 2", "Some description 2", LocalDateTime.now());
        orderService.save(order1);
        orderService.save(order2);
        Order order = orderService.findById(order1.getId());
        assertThat(order.getDescription()).isEqualTo("Some description 1");
        assertThat(order.getId()).isEqualTo(1);
    }

    @Test
    void whenSaveTwoOrdersAndUpdateFirstOrder() {
        Order order1 = new Order("Some order 1", "Some description 1", LocalDateTime.now());
        Order order2 = new Order("Some order 2", "Some description 2", LocalDateTime.now());
        orderService.save(order1);
        orderService.save(order2);
        Order order = orderService.findById(order1.getId());
        order.setName("Primus, ferox zetas semper imperium de fidelis, placidus cursus.");
        order.setDescription("Nunquam tractare torquis.");
        orderService.update(order);
        List<Order> orders = (List<Order>) orderService.findAll();
        assertThat(orders.size()).isEqualTo(2);
        assertThat(orders.get(0).getName()).isEqualTo("Primus, ferox zetas semper imperium de fidelis, placidus cursus.");
        assertThat(orders.get(0).getDescription()).isEqualTo("Nunquam tractare torquis.");
        assertThat(orders.get(0).getId()).isEqualTo(1);
        assertThat(orders.get(1).getName()).isEqualTo("Some order 2");
        assertThat(orders.get(1).getDescription()).isEqualTo("Some description 2");
        assertThat(orders.get(1).getId()).isEqualTo(2);
    }

    @Test
    void whenSaveTwoOrdersAndFindFirstByName() {
        Order order1 = new Order("Pius navis mechanice dignuss contencio est.", "Some description 1", LocalDateTime.now());
        Order order2 = new Order("Ususs velum in vierium!", "Some description 2", LocalDateTime.now());
        orderService.save(order1);
        orderService.save(order2);
        Optional<Order> order = orderService.findByName(order1.getName());
        assertThat(order).isNotEmpty();
        assertThat(order.get().getId()).isEqualTo(1);
        assertThat(order.get().getName()).isEqualTo("Pius navis mechanice dignuss contencio est.");
    }

    @AfterEach
    public void wipeOrderTable() {
        //  TRUNCATE TABLE T RESTART IDENTITY AND COMMIT NO CHECK
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}