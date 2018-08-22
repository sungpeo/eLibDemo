package com.elib.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class GoldenMaster {

    public static final String GOLDENMASTER_TXT = "goldenmaster/goldenmaster.txt";

    private static String INPUT_STRING_FOR_SIMULATATION = "printUser 0\n"
            + "printDoc 1\n"
            + "printLoans\n"
//				+ "isHolding 0, 1\n"
            + "searchUser Paula\n"
            + "searchDoc Refactoring\n"
            + "returnDoc 0\n"
            + "borrowDoc 0, 2\n"
            + "rmDoc 3\n"
            + "rmUser 3\n"
            + "addUser Kershaw, LA, 010-2220-2345\n"
            + "addStaff Furguson, London, 010-3456-1111, A777\n"
            + "addBook CQRS, Young, ISBN8938\n"
            + "addReport Event Storming, Brandolini, TX0287, Consulting\n"
            + "addJournal Software Architectures\n"
            + "exit\n"; // Bye

    public void generateGoldenMaster() throws IOException {
        Files.write(Paths.get(GOLDENMASTER_TXT), runResultBytes());
    }

    public String readCurrentGoldenMaster() throws IOException {
        return Files.readAllLines(Paths.get(GOLDENMASTER_TXT)).stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public byte[] runResultBytes() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        ByteArrayInputStream bais = new ByteArrayInputStream(INPUT_STRING_FOR_SIMULATATION.getBytes());
        System.setIn(bais);

        CmdUI ui = new CmdUI();
        ui.start();

        return baos.toByteArray();
    }
}
