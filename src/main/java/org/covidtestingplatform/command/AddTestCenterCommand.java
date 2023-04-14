package org.covidtestingplatform.command;

import org.covidtestingplatform.model.Coordinates;
import org.covidtestingplatform.model.TestingCentre;
import org.covidtestingplatform.model.User;
import org.covidtestingplatform.service.TestingCentreService;
import org.covidtestingplatform.service.UserService;

public class AddTestCenterCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.println("Command: Add Test Centre");
        TestingCentreService tcService = TestingCentreService.get();
        String tcId = tcService.addTestingCenter(args[0], new Coordinates(Integer.valueOf(args[1]),Integer.valueOf(args[2])));
        TestingCentre tc = tcService.getTestCenter(tcId);
        System.out.println(String.format("Test centre with %s and Name %s created.", tc.getId(), tc.getName()));
    }
}
