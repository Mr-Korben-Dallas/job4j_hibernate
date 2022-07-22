package ru.job4j.hql;

import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hql.model.Candidate;
import ru.job4j.hql.repository.CandidateStore;
import ru.job4j.hql.service.CandidateService;

@Log4j2
public class HibernateRunner {
    private static final CandidateService CANDIDATESERVICE = new CandidateService(new CandidateStore());

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Candidate firstCandidate = new Candidate("John", "Rectors tolerare!", 100000);
            Candidate secondCandidate = new Candidate("Jenny", "Sailors whine with urchin!", 200000);
            Candidate thirdCandidate = new Candidate("Tom", "The lord knows thought which is not unprepared.", 300000);
            Candidate createdFirstCandidate = CANDIDATESERVICE.persist(firstCandidate, sf);
            Candidate createdSecondCandidate = CANDIDATESERVICE.persist(secondCandidate, sf);
            Candidate createdThirdCandidate =  CANDIDATESERVICE.persist(thirdCandidate, sf);
            createdFirstCandidate.setName("createdFirstCandidate");
            createdSecondCandidate.setName("createdSecondCandidate");
            createdThirdCandidate.setName("createdThirdCandidate");
            CANDIDATESERVICE.update(createdFirstCandidate, sf);
            CANDIDATESERVICE.update(createdSecondCandidate, sf);
            CANDIDATESERVICE.update(createdThirdCandidate, sf);
            Candidate firstByName = CANDIDATESERVICE.findByName("createdFirstCandidate", sf);
            Candidate secondByName = CANDIDATESERVICE.findByName("createdSecondCandidate", sf);
            Candidate thirdByName = CANDIDATESERVICE.findByName("createdThirdCandidate", sf);
            Candidate firstById = CANDIDATESERVICE.findById(firstByName.getId(), sf);
            Candidate secondById = CANDIDATESERVICE.findById(secondByName.getId(), sf);
            Candidate thirdById = CANDIDATESERVICE.findById(thirdByName.getId(), sf);
            int result = CANDIDATESERVICE.delete(firstById.getId(), sf);
            System.out.println(result);
            result = CANDIDATESERVICE.delete(secondById.getId(), sf);
            System.out.println(result);
            result = CANDIDATESERVICE.delete(thirdById.getId(), sf);
            System.out.println(result);
        }  catch (Exception e) {
            log.error("Error in main", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
