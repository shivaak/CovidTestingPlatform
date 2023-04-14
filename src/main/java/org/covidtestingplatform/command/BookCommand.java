package org.covidtestingplatform.command;


import org.covidtestingplatform.service.TestingCentreService;
import org.covidtestingplatform.service.UserService;

public class BookCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.println("Command: Book Test Center");
        TestingCentreService tcService = TestingCentreService.get();
        UserService userService = UserService.get();
        tcService.book_test(userService.getUser(args[0].toLowerCase()));
    }
}
