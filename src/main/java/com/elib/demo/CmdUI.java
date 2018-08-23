package com.elib.demo;

import java.util.Scanner;

public class CmdUI {

    private Dispatcher dispatcher;
    public CmdUI (Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void start() {

        this.dispatcher.generateSamples();
        printMenu();

        String s = "";
        Scanner scanner = new Scanner(System.in);
        s = scanner.nextLine();
        while (!s.equals("exit")) {
            this.dispatcher.dispatchCommand(s);

            printMenu();
            s = scanner.nextLine();
        }

        scanner.close();
    }

    public void printMenu() {
        System.out.println("\nCOMMANDS:");
        System.out.println("addUser name, address, phone");
        System.out.println("addStaff name, address, phone, id");
        System.out.println("rmUser userld");
        System.out.println("addBook title, authors, ISBN");
        System.out.println("addReport title, authors, refs, organization");
        System.out.println("addJournal title");
        System.out.println("rmDoc docid");
        System.out.println("borrowDoc userld, docId");
        System.out.println("returnDoc docId");
        System.out.println("searchUser name");
        System.out.println("searchDoc title");
        System.out.println("isHolding userld, docId");
        System.out.println("printLoans");
        System.out.println("printUser userld");
        System.out.println("printDoc docId");
        System.out.println("exit\n\n");
    }




}
