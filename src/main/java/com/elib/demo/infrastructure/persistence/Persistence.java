package com.elib.demo.infrastructure.persistence;

import com.elib.demo.domain.document.Document;
import com.elib.demo.domain.document.Loan;
import com.elib.demo.domain.user.User;

import javax.persistence.TypedQuery;
import java.util.List;

public interface Persistence {

    TypedQuery<Loan> createLoanQuery(User user, Document doc);

    User findUser(int userCode);

    Document findDocument(int docCode);

    Loan findLoan(User user, Document doc);

    List<User> findAllUsers();

    List<Document> findAllDocuments();

    List<Loan> findAllLoans();


    void saveUser(User user);

    void removeUser(User user);

    void saveDocument(Document doc);

    void removeDocument(Document doc);



}
