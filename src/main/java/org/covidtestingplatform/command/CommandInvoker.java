package org.covidtestingplatform.command;

import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {
    private Map<String, Command> commandMap = new HashMap<>();

    public void registerCommand(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    public void executeCommand(String commandName, String[] args) {
        Command command = commandMap.get(commandName);
        if (command != null && args!=null) {
            command.execute(args);
        } else {
            System.out.println("Invalid command");
        }
    }
}