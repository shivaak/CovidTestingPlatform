package org.covidtestingplatform.command;


import org.covidtestingplatform.model.Coordinates;
import org.covidtestingplatform.model.User;
import org.covidtestingplatform.service.UserService;

public class ListUserCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.println("Command: List User");
        UserService userService = UserService.get();
        userService.printAll();
    }
}
