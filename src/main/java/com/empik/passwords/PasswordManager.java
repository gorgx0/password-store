package com.empik.passwords;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name="passwords manager",mixinStandardHelpOptions = true, version = "0.0.1",

        subcommands = {
            PasswordManager.AddPassword.class,
            PasswordManager.DeletePassword.class,
            PasswordManager.GetPassword.class
        }
)
public class PasswordManager {

    public static void main(String[] args) {
        //noinspection InstantiationOfUtilityClass
        System.exit(new CommandLine(new PasswordManager()).execute(args));
    }

    @Command(name = "add")
    static class AddPassword implements Callable<Integer>{
        @Override
        public Integer call() {
            System.out.println("add password");
            return null;
        }
    }

    @Command(name = "delete")
    static class DeletePassword implements Callable<Integer> {

        @Override
        public Integer call() {
            System.out.println("delete password");
            return null;
        }
    }

    @Command(name = "get")
    static class GetPassword implements Callable<Integer> {

        @Override
        public Integer call() {
            System.out.println("get password");
            return null;
        }
    }
}
