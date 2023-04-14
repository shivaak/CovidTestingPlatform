package org.covidtestingplatform.command;


import org.covidtestingplatform.service.TestingCentreService;
import org.covidtestingplatform.service.UserService;

public class UpdateCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.println("Command: Update Vaccination count in TC");
        TestingCentreService tcService = TestingCentreService.get();
        tcService.provideKit(args[0].toLowerCase(), Integer.valueOf(args[1]));
        tcService.printTestCentre(args[0].toLowerCase());
    }
}
