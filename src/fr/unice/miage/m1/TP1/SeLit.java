package fr.unice.miage.m1.TP1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Class created on 05/10/2016
 *
 * @author JuIngong
 */

public class SeLit {
    void lecture(Scanner source) {

        while(source.hasNextLine()) {
            String s = source.nextLine();
            if(!s.replaceAll("[ \\t] ", "").startsWith("//")) {
                System.out.println("LU:" + s);
            }
            //Test
        }
    }
    void ecriture(Scanner source) {
        File file = new File("output.txt");
        PrintStream printStream =  null;
        PrintStream stdout = System.out;
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            printStream = new PrintStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setOut(printStream);
        while(source.hasNextLine()) {
            String s = source.nextLine();
            if(!s.replaceAll("[ \\t] ", "").startsWith("//")) {
                System.out.println(s);
            }

            //Test
        }
        printStream.close();
        System.setOut(stdout);
        System.out.println("End of the write");
    }

    static public void main(String[] args) {
        File file = new File(".\\src\\fr\\unice\\miage\\m1\\TP1\\SeLit.java");

        try {
            new SeLit().ecriture(new Scanner(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}