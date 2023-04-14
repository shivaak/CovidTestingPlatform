package org.covidtestingplatform.command;


import org.covidtestingplatform.service.TestingCentreService;
import org.covidtestingplatform.service.UserService;

public class ListTcCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.println("Command: List Test Centre");
        TestingCentreService tcService = TestingCentreService.get();
        tcService.printAll();
    }
}
