package ru.job4j.hql.service;

import org.hibernate.SessionFactory;
import ru.job4j.hql.model.Candidate;
import ru.job4j.hql.repository.CandidateStore;

import java.util.List;

public class CandidateService {
    private final CandidateStore store;

    public CandidateService(CandidateStore store) {
        this.store = store;
    }

    public Candidate persist(Candidate candidate, SessionFactory sf) {
        return store.persist(candidate, sf);
    }

    public Candidate findById(int id, SessionFactory sf) {
        return store.findById(id, sf);
    }

    public List<Candidate> findByName(String name, SessionFactory sf) {
        return store.findByName(name, sf);
    }

    public int update(Candidate candidate, SessionFactory sf) {
        return store.update(candidate, sf);
    }

    public int delete(int id, SessionFactory sf) {
        return store.delete(id, sf);
    }
}
