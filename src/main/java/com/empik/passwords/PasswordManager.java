package com.empik.passwords;

import org.freedesktop.secret.simple.SimpleCollection;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.util.Scanner;

@Command(name = "passwords manager", mixinStandardHelpOptions = true, version = "0.0.1")
public class PasswordManager {

    static Scanner scanner = new Scanner(System.in);
    @Option(names = {"-c", "--collectionName"}, defaultValue = "Empik")
    String collectionName;

    public static void main(String[] args) {
        //noinspection InstantiationOfUtilityClass
        System.exit(new CommandLine(new PasswordManager()).execute(args));
    }


    @Command(name = "add", addMethodSubcommands = false)
    void add(
            @Option(names = {"-l", "--label"}, interactive = true, hidden = false, required = true, prompt = "Enter label: ")
            String label,
            @Option(names = {"-p", "--password"}, interactive = true, hidden = false, required = true, prompt = "Enter password: ")
            String password
    ) throws IOException {
        System.out.println("Enter password label: ");
        System.out.println("Enter password: ");
        SimpleCollection collection = new SimpleCollection(collectionName, null);
        collection.createItem(label, new String(password));
    }

    @Command(name = "delete")
    void delete() {
        System.out.println("delete password");
    }

    @Command(name = "get")
    void get() {
        System.out.println("get password");
    }
}
