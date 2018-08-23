package com.elib.demo.domain.document;

import com.elib.demo.domain.user.User;
import com.elib.demo.infrastructure.persistence.Persistence;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class DocumentService {

    public DocumentService (Persistence persistence) {
        this.persistence = persistence;
    }

    private Persistence persistence;

    static final int MAX_NUMBER_OF_LOANS = 2;

    public void addBook(String title, String authors, String ISBNCode) {
        Document doc = new Document(title, authors, ISBNCode);

        persistence.saveDocument(doc);

        System.out.println("Added doc: " + doc.getCode() + " - " + doc.getTitle());

    }

    public void addReport(String title, String authors, String refNo, String organization) {
        Document doc = new Document(title, authors, refNo, organization);

        persistence.saveDocument(doc);

        System.out.println("Added doc: " + doc.getCode() + " - " + doc.getTitle());

    }

    public void addJournal(String title) {
        Document doc = new Document(title);

        persistence.saveDocument(doc);

        System.out.println("Added doc: " + doc.getCode() + " - " + doc.getTitle());

    }

    public void rmDocument(int docCode) {
        Document doc = persistence.findDocument(docCode);

        if (doc == null || doc.isOut())
            return;

        persistence.removeDocument(doc);

        System.out.println("Removed doc: " + doc.getCode() + " - " + doc.getTitle());
    }

    public void searchDocument(String title) {
        List<Document> docs = persistence.findAllDocuments();

        List<Document> docsFound = new LinkedList<>();
        for (Document doc : docs) {
            if (doc.getTitle().indexOf(title) != -1)
                docsFound.add(doc);
        }

        for (Document doc : docsFound) {
            System.out.println("Doc found: " + doc.getCode() + " - " + doc.getTitle());
        }
    }

    public void printDoc(int docCode) {
        Document doc = persistence.findDocument(docCode);
        if (doc != null)
            doc.printInfo();
    }

    public void borrowDoc(int userCode, int docCode) {

        User user = persistence.findUser(userCode);
        Document doc = persistence.findDocument(docCode);
        if (user == null || doc == null)
            return;

        if (user.numberOfLoans() < MAX_NUMBER_OF_LOANS && doc.isAvailable() && doc.authorizedLoan(user)
                && user.getLatePenalty() == 0) {
            Loan loan = new Loan(user, doc);

            addLoan(loan);

            System.out.println("New loan: " + user.getName() + " - " + doc.getTitle());
        }
    }

    public void returnDocument(int docCode) {
        Document doc = persistence.findDocument(docCode);
        if (doc == null)
            return;

        User user = doc.getBorrower();
        if (user == null)
            return;

        if (doc.isOut()) {
            Loan loan = persistence.findLoan(user, doc);
            if (loan == null)
                return;

            loan.getUser().addCharge(loan.getLateCharge(LocalDate.now()));

            rmLoan(loan);

            System.out.println("Loan closed: " + user.getName() + " - " + doc.getTitle());
        }
    }

    public void isHolding(int userCode, int docCode) {
        User user = persistence.findUser(userCode);
        Document doc = persistence.findDocument(docCode);

        if (user == null || doc == null)
            return;

        if (persistence.findLoan(user, doc) != null)
            System.out.println(user.getName() + " is holding " + doc.getTitle());
        else
            System.out.println(user.getName() + " is not holding " + doc.getTitle());
    }

    public void printAllLoans() {
        persistence.findAllLoans().stream().forEach(Loan::print);
    }

    private void addLoan(Loan loan) {
        if (loan == null)
            return;

        User user = loan.getUser();
        Document doc = loan.getDocument();

        user.addLoan(loan);
        persistence.saveUser(user);

        doc.addLoan(loan);
        persistence.saveDocument(doc);
    }

    private void rmLoan(Loan loan) {
        if (loan == null)
            return;

        User user = loan.getUser();
        Document doc = loan.getDocument();

        user.removeLoan(loan);
        persistence.saveUser(user);

        doc.removeLoan();
        persistence.saveDocument(doc);
    }

}
