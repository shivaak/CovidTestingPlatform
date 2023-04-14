package org.covidtestingplatform.command;


import org.covidtestingplatform.model.Coordinates;
import org.covidtestingplatform.model.User;
import org.covidtestingplatform.service.UserService;

public class AddUserCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.println("Command: Add User");
        UserService userService = UserService.get();
        User user = new User(args[0], new Coordinates(Integer.valueOf(args[1]), Integer.valueOf(args[2])));
        userService.addUser(user);
        System.out.println(String.format("User with %s and Name %s created.", user.getId(), user.getName()));
    }
}
