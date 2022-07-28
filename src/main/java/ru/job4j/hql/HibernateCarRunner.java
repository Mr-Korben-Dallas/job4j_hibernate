package ru.job4j.hql;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hql.model.CarBrand;
import ru.job4j.hql.model.CarModel;

@Log4j2
public class HibernateCarRunner {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            CarModel carModel1 = new CarModel("Sport model");
            CarModel carModel2 = new CarModel("Free road model");
            CarModel carModel3 = new CarModel("Free road model 2");
            CarModel carModel4 = new CarModel("Free road model 3");
            CarModel carModel5 = new CarModel("Free road model 4");
            session.persist(carModel1);
            session.persist(carModel2);
            session.persist(carModel3);
            session.persist(carModel4);
            session.persist(carModel5);
            CarBrand carBrand = new CarBrand("Ford");
            carBrand.addCar(session.get(CarModel.class, carModel1.getId()));
            carBrand.addCar(session.get(CarModel.class, carModel2.getId()));
            carBrand.addCar(session.get(CarModel.class, carModel3.getId()));
            carBrand.addCar(session.get(CarModel.class, carModel4.getId()));
            carBrand.addCar(session.get(CarModel.class, carModel5.getId()));
            session.persist(carBrand);
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            log.error("Error in main", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
