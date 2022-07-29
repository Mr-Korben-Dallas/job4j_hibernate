package ru.job4j.hql;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hql.model.Author;
import ru.job4j.hql.model.Book;

@Log4j2
public class HibernateBookAuthorRunner {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Book book1 = new Book("PHP");
            Book book2 = new Book("C++");
            Book book3 = new Book("Java");
            Author author1 = new Author("John Doe");
            Author author2 = new Author("Dave Minter");
            Author author3 = new Author("Jeff Linwood");
            author1.getBooks().add(book1);
            author1.getBooks().add(book2);
            author2.getBooks().add(book2);
            author3.getBooks().add(book3);
            session.persist(author1);
            session.persist(author2);
            session.persist(author3);
            session.remove(author1);
            session.remove(author2);
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            log.error("Error in main", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
