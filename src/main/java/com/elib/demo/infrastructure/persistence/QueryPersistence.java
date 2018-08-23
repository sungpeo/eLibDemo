package com.elib.demo.infrastructure.persistence;

import com.elib.demo.domain.document.Document;
import com.elib.demo.domain.document.Loan;
import com.elib.demo.domain.user.User;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

public class QueryPersistence implements Persistence {

    private EntityManager em;
    private QueryPersistence() {
        EntityManagerFactory emFactory = javax.persistence.Persistence.createEntityManagerFactory("elibdemo");
        this.em = emFactory.createEntityManager();
    }
    public static QueryPersistence getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final QueryPersistence INSTANCE = new QueryPersistence();
    }


    @Override
    public TypedQuery<Loan> createLoanQuery(User user, Document doc) {
        return em.createQuery("SELECT c FROM Loan c WHERE c.user = :user and c.document = :doc", Loan.class)
                .setParameter("user", user).setParameter("doc", doc);
    }

    @Override
    public User findUser(int userCode) {
        return em.find(User.class, userCode);
    }

    @Override
    public Document findDocument(int docCode) {
        return em.find(Document.class, docCode);
    }

    @Override
    public Loan findLoan(User user, Document doc) {
        TypedQuery<Loan> query = createLoanQuery(user, doc);
        Loan loan = query.getSingleResult();

        return loan;
    }

    @Override
    public List<User> findAllUsers() {
        TypedQuery<User> query = em.createQuery("SELECT c FROM User c", User.class);
        return query.getResultList();
    }

    @Override
    public List<Document> findAllDocuments() {
        TypedQuery<Document> query = em.createQuery("SELECT c FROM Document c", Document.class);
        List<Document> docs = query.getResultList();
        return docs;
    }

    @Override
    public List<Loan> findAllLoans() {
        TypedQuery<User> query = em.createQuery("SELECT c FROM User c", User.class);
        List<User> users = query.getResultList();
        return users.stream().flatMap(u -> u.getLoans().stream()).collect(Collectors.toList());

    }

    @Override
    public void saveUser(User user) {
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            return;
        }
    }

    @Override
    public void removeUser(User user) {
        try {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            return;
        }
    }

    @Override
    public void saveDocument(Document doc) {
        try {
            em.getTransaction().begin();
            em.persist(doc);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            return;
        }
        return;
    }

    @Override
    public void removeDocument(Document doc) {
        try {
            em.getTransaction().begin();
            em.remove(doc);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            return;
        }
    }

    /*
     * Public methods that depend on JPA
     */
    public int numberOfDocuments() {
        Query query = em.createQuery("SELECT COUNT(d) FROM Document d");
        return (Integer) query.getSingleResult();
    }

    public int numberOfUsers() {
        Query query = em.createQuery("SELECT COUNT(d) FROM User d");
        return (Integer) query.getSingleResult();
    }

    public int numberOfLoans() {
        Query query = em.createQuery("SELECT COUNT(d) FROM Loan d");
        return (Integer) query.getSingleResult();
    }
}
