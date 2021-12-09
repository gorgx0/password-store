package com.empik.passwords;

import org.freedesktop.secret.simple.SimpleCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.Console;
import java.io.IOException;

class CommonOptions {
    @Option(names = {"-c", "--collectionName"}, defaultValue = "Empik")
    String collectionName;
    @Option(names = {"-l", "--label"},  required = true)
    String label;
}

@Command(name = "passwords manager", mixinStandardHelpOptions = true, version = "0.0.1")
public class PasswordManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordManager.class);

    public static void main(String[] args) {
        System.exit(new CommandLine(new PasswordManager()).execute(args));
    }


    @Command(name = "add", addMethodSubcommands = false)
    void add(@CommandLine.Mixin CommonOptions commonOptions) throws IOException {
        SimpleCollection collection = new SimpleCollection(commonOptions.collectionName, null);
        String password;
        String passwordConfirmed;
        Console console = System.console();
        while (true) {
            console.printf("Enter password: ");
            password = String.valueOf(console.readPassword());
            console.printf("Repeat password: ");
            passwordConfirmed = String.valueOf(console.readPassword());
            if(password.equals(passwordConfirmed)) {
                break;
            }
            System.out.println("Entered passwords dont match. Try again.");
        }
        String path = collection.createItem(commonOptions.label, password);
        LOGGER.info("Created secret with path: {}", path);
    }

    @Command(name = "delete")
    void delete(@CommandLine.Mixin CommonOptions commonOptions) throws IOException {
        SimpleCollection collection = new SimpleCollection(commonOptions.collectionName, null);
        collection.getSecrets().forEach((path, secret) -> {
            String label = collection.getLabel(path);
            if(label.equals(commonOptions.label)) {
                System.out.printf("Found secret for label %s\n", label);
                collection.deleteItem(path);
                System.out.printf("Secret for label %s\n and path %s removed", label, path);
            }
        } );
    }

    @Command(name = "get")
    void get(@CommandLine.Mixin CommonOptions commonOptions) throws IOException {
        LOGGER.info("Getting password for {} from collection {}", commonOptions.label, commonOptions.collectionName);

        SimpleCollection collection = new SimpleCollection(commonOptions.collectionName, null);
        collection.getSecrets().forEach((path, secret) -> {
            String label = collection.getLabel(path);
            if(label.equals(commonOptions.label)) {
                System.out.printf("Found secret for label %s = %s\n", label, String.valueOf(secret));
            }
        } );
    }
}
