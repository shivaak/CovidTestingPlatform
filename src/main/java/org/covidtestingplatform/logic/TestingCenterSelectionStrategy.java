package org.covidtestingplatform.logic;

import org.covidtestingplatform.model.TestingCentre;
import org.covidtestingplatform.model.User;

import java.util.List;
import java.util.Map;

public interface TestingCenterSelectionStrategy {
    List<TestingCentre> selectTestingCenters(User user, Map<String, TestingCentre> testingCentreMap, int numberOfCentres);
}
