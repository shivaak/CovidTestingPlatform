package org.covidtestingplatform;

import org.covidtestingplatform.command.*;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        CommandInvoker invoker = new CommandInvoker();
        invoker.registerCommand("ADD USER", new AddUserCommand());
        invoker.registerCommand("ADD TC", new AddTestCenterCommand());
        invoker.registerCommand("LIST USER", new ListUserCommand());
        invoker.registerCommand("LIST TC", new ListTcCommand());
        invoker.registerCommand("BOOK", new BookCommand());
        invoker.registerCommand("UPDATE", new UpdateCommand());

        String[] initCommand = {
                "ADD USER USER1 1 2",
                "ADD USER USER2 2 3",
                "ADD USER USER3 3 4",
                "ADD USER USER4 4 5",
                "ADD USER USER5 5 6",
                "ADD TC BTM 1 1",
                "ADD TC SARJAPUR 5 5"
        };

        for(String cmd : initCommand){
            executeHelper(invoker, cmd);
        }

        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter command: ");
            String input = scanner.nextLine().toUpperCase();
            if(input.equalsIgnoreCase("EXIT")){
                break;
            }
            executeHelper(invoker, input);
        }
    }

    private static void executeHelper(CommandInvoker invoker, String input){
        input=input.toUpperCase();
        String[] tokens = input.split(" ");
        StringBuilder command = new StringBuilder();
        String[] commandArgs = null;

        boolean valid = true;
        try{
            switch(tokens[0]){
                case "ADD":
                case "LIST":
                    command.append(tokens[0]).append(" ").append(tokens[1]);
                    commandArgs = new String[tokens.length - 1];
                    System.arraycopy(tokens, 2, commandArgs, 0, tokens.length - 2);
                    break;
                case "BOOK":
                case "UPDATE":
                    command.append(tokens[0]);
                    commandArgs = new String[tokens.length - 1];
                    System.arraycopy(tokens, 1, commandArgs, 0, tokens.length - 1);
                    break;
                default:
                    valid=false;
                    System.out.println("Invalid Command");
            }
            if(valid)invoker.executeCommand(command.toString(), commandArgs);
        }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }



    }
}