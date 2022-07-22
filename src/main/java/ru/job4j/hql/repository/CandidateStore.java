package ru.job4j.hql.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.hql.model.Candidate;

public class CandidateStore {
    public Candidate persist(Candidate candidate, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.persist(candidate);
        session.getTransaction().commit();
        session.close();
        return candidate;
    }

    public Candidate findById(int id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<Candidate> query = session.createQuery("from Candidate c where c.id = :id", Candidate.class);
        query.setParameter("id", id);
        Candidate candidate = query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return candidate;
    }

    public Candidate findByName(String name, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<Candidate> query = session.createQuery("from Candidate c where c.name = :name", Candidate.class);
        query.setParameter("name", name);
        Candidate candidate = query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return candidate;
    }

    public int update(Candidate candidate, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        int affectedRows = session
                .createQuery("update Candidate set name = :name, experience = :experience, salary = :salary where id = :id")
                .setParameter("name", candidate.getName())
                .setParameter("experience", candidate.getExperience())
                .setParameter("salary", candidate.getSalary())
                .setParameter("id", candidate.getId())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return affectedRows;
    }

    public int delete(int id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        int affectedRows = session
                .createQuery("delete from Candidate where id = :id")
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return affectedRows;
    }
}
