package ru.job4j.hql;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hql.model.CarBrand;
import ru.job4j.hql.model.CarModel;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.query.Query;

@Log4j2
public class HibernateCarRunner {
    public static void main(String[] args) {
        List<CarBrand> result = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            CarBrand carBrand = new CarBrand("Ford");
            session.persist(carBrand);
            CarModel carModel1 = new CarModel("Sport model", carBrand);
            CarModel carModel2 = new CarModel("Free road model", carBrand);
            CarModel carModel3 = new CarModel("Free road model 2", carBrand);
            session.persist(carModel1);
            session.persist(carModel2);
            session.persist(carModel3);
            Query<CarBrand> query = session.createQuery("select distinct c from CarBrand c join fetch c.cars", CarBrand.class);
            result = query.list();
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            log.error("Error in main", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

        for (CarModel carModel: result.get(0).getCars()) {
            System.out.println(carModel);
        }
    }
}
