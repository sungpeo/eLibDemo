package com.elib.demo;

import com.elib.demo.domain.document.DocumentService;
import com.elib.demo.domain.user.UserService;
import com.elib.demo.infrastructure.persistence.Persistence;

public class Dispatcher {

    private Dispatcher (Persistence persistence) {
        this.userService = new UserService(persistence);
        this.documentService = new DocumentService(persistence);

    }

    public static Dispatcher createDispatcher (Persistence persistence) {
        return new Dispatcher(persistence);
    }


    private UserService userService;
    private DocumentService documentService;



    public void dispatchCommand(String cmd) {
        if (cmd.startsWith("addUser"))
            addUser(cmd);
        if (cmd.startsWith("addStaff"))
            addStaff(cmd);
        if (cmd.startsWith("rmUser"))
            rmUser(cmd);
        if (cmd.startsWith("addBook"))
            addBook(cmd);
        if (cmd.startsWith("addReport"))
            addReport(cmd);
        if (cmd.startsWith("addJournal"))
            addJournal(cmd);
        if (cmd.startsWith("rmDoc"))
            rmDocument(cmd);
        if (cmd.startsWith("borrowDoc"))
            borrowDoc(cmd);
        if (cmd.startsWith("returnDoc"))
            returnDocument(cmd);
        if (cmd.startsWith("searchUser"))
            searchUser(cmd);
        if (cmd.startsWith("searchDoc"))
            searchDocument(cmd);
        if (cmd.startsWith("isHolding"))
            isHolding(cmd);
        if (cmd.startsWith("printLoans"))
            printAllLoans();
        if (cmd.startsWith("printUser"))
            printUser(cmd);
        if (cmd.startsWith("printDoc"))
            printDoc(cmd);
    }
    /*
     * User management commands
     */
    public void addUser(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 3)
            return;

        userService.addUser(args[0], args[1], args[2]);

    }

    public void addStaff(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 4)
            return;

        userService.addStaff(args[0], args[1], args[2], args[3]);
    }

    public void rmUser(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 1)
            return;

        int userCode = Integer.parseInt(args[0]);

        userService.rmUser(userCode);
    }

    public void searchUser(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 1)
            return;

        String name = args[0];
        userService.searchUser(name);

    }

    public void printUser(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 1)
            return;

        int userCode = Integer.parseInt(args[0]);
        userService.printUser(userCode);
    }

    /*
     * Document management commands
     */
    public void addBook(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 3)
            return;

        documentService.addBook(args[0], args[1], args[2]);
    }

    public void addReport(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 4)
            return;

        documentService.addReport(args[0], args[1], args[2], args[3]);

    }

    public void addJournal(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 1)
            return;

        documentService.addJournal(args[0]);

    }

    public void rmDocument(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 1)
            return;

        int docCode = Integer.parseInt(args[0]);

        documentService.rmDocument(docCode);
    }

    public void searchDocument(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 1)
            return;

        String title = args[0];

        documentService.searchDocument(title);
    }

    public void printDoc(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 1)
            return;

        int docCode = Integer.parseInt(args[0]);
        documentService.printDoc(docCode);
    }

    /*
     * Loan management commands
     */
    public void borrowDoc(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 2)
            return;

        int userCode = Integer.parseInt(args[0]);
        int docCode = Integer.parseInt(args[1]);

        documentService.borrowDoc(userCode, docCode);
    }

    public void returnDocument(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 1)
            return;

        int docCode = Integer.parseInt(args[0]);
        documentService.returnDocument(docCode);
    }

    public void isHolding(String cmd) {
        String args[] = getArgs(cmd);
        if (args.length < 2)
            return;

        int userCode = Integer.parseInt(args[0]);
        int docCode = Integer.parseInt(args[1]);

        documentService.isHolding(userCode, docCode);

    }

    public void printAllLoans() {
        documentService.printAllLoans();
    }



    /*
     *
     */
//    private void addLoan(Loan loan) {
//        if (loan == null)
//            return;
//
//        User user = loan.getUser();
//        Document doc = loan.getDocument();
//
//        user.addLoan(loan);
//        persistence.saveUser(user);
//
//        doc.addLoan(loan);
//        persistence.saveDocument(doc);
//    }

//    private void rmLoan(Loan loan) {
//        if (loan == null)
//            return;
//
//        User user = loan.getUser();
//        Document doc = loan.getDocument();
//
//        user.removeLoan(loan);
//        persistence.saveUser(user);
//
//        doc.removeLoan();
//        persistence.saveDocument(doc);
//    }

    public String[] getArgs(String cmd) {
        String args[] = new String[0];
        String s = cmd.trim();
        if (s.indexOf(" ") != -1) {
            s = s.substring(s.indexOf(" "));
            args = s.trim().split(",");
            for (int i = 0; i < args.length; i++)
                args[i] = args[i].trim();
        }
        return args;
    }



    /*
     * Database Access private methods
     */
//    private TypedQuery<Loan> createLoanQuery(User user, Document doc) {
//        return em.createQuery("SELECT c FROM Loan c WHERE c.user = :user and c.document = :doc", Loan.class)
//                .setParameter("user", user).setParameter("doc", doc);
//    }

//    User findUser(int userCode) {
//        return em.find(User.class, userCode);
//    }

//    private Document findDocument(int docCode) {
//        return em.find(Document.class, docCode);
//    }

//    private Loan findLoan(User user, Document doc) {
//        TypedQuery<Loan> query = createLoanQuery(user, doc);
//        Loan loan = query.getSingleResult();
//
//        return loan;
//    }

//    private List<User> findAllUsers() {
//        TypedQuery<User> query = em.createQuery("SELECT c FROM User c", User.class);
//        return query.getResultList();
//    }

//    private List<Document> findAllDocuments() {
//        TypedQuery<Document> query = em.createQuery("SELECT c FROM Document c", Document.class);
//        List<Document> docs = query.getResultList();
//        return docs;
//    }

//    private List<Loan> findAllLoans() {
//        TypedQuery<User> query = em.createQuery("SELECT c FROM User c", User.class);
//        List<User> users = query.getResultList();
//        return users.stream().flatMap(u -> u.getLoans().stream()).collect(Collectors.toList());
//    }

//    private void saveUser(User user) {
//        try {
//            em.getTransaction().begin();
//            em.persist(user);
//            em.getTransaction().commit();
//        } catch (PersistenceException e) {
//            return;
//        }
//    }

//    private void removeUser(User user) {
//        try {
//            em.getTransaction().begin();
//            em.remove(user);
//            em.getTransaction().commit();
//        } catch (PersistenceException e) {
//            return;
//        }
//    }

//    private void saveDocument(Document doc) {
//        try {
//            em.getTransaction().begin();
//            em.persist(doc);
//            em.getTransaction().commit();
//        } catch (PersistenceException e) {
//            return;
//        }
//        return;
//    }

//    private void removeDocument(Document doc) {
//        try {
//            em.getTransaction().begin();
//            em.remove(doc);
//            em.getTransaction().commit();
//        } catch (PersistenceException e) {
//            return;
//        }
//    }

    public void generateSamples() {
        addUser("addUser Paula, Seoul, 010-234-1837");
        addUser("addUser Michael, Washington, 515-234-5667");
        addUser("addUser Neuman, Seatle, 511-927-3857");
        addStaff("addUser John, Pusan, 010-563-5827, A001");
        addStaff("addUser Peter, Ansan, 010-8765-1038, A002");
        addBook("addBook Refactoring, Fowler, ISBN001");
        addBook("addBook Clean Code, Martin, ISBN002");
        addBook("addBook Design Patterns, GOF, ISBN003");
        addJournal("addJournal Communications of ACM");
        addReport("addReport Software Architecture, J.S. Kim, TX001-0284, Hanyang University");

        borrowDoc("borrowDoc 0, 0");
        borrowDoc("borrowDoc 1, 1");
    }

}
