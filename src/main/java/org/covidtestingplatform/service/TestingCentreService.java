package org.covidtestingplatform.service;

import org.covidtestingplatform.logic.NClosestTestingCenter;
import org.covidtestingplatform.logic.TestingCenterSelectionStrategy;
import org.covidtestingplatform.model.Coordinates;
import org.covidtestingplatform.model.TestingCentre;
import org.covidtestingplatform.model.User;

import java.util.*;

public class TestingCentreService {

    private static final TestingCentreService testingCentreService = new TestingCentreService();

    private Map<String, TestingCentre> testingCentreMap = new HashMap<>();

    private int N = 3;
    private TestingCenterSelectionStrategy testingCenterSelectionStrategy;


    private TestingCentreService(){
        testingCenterSelectionStrategy = new NClosestTestingCenter();
    }
    public static TestingCentreService get(){
        return  testingCentreService;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        this.N = n;
    }

    public void setTestingCenterSelectionStrategy(TestingCenterSelectionStrategy testingCenterSelectionStrategy) {
        this.testingCenterSelectionStrategy = testingCenterSelectionStrategy;
    }

    public String addTestingCenter(String name, Coordinates location){
        // TODO : Validate if the testing center with given name and location already exists

        TestingCentre tc = new TestingCentre(name, location);
        testingCentreMap.put(tc.getId(), tc);
        return tc.getId();
    }

    public void book_test(User user) {
        if(user.isVaccinationProvided()){
            System.out.println("Vaccination already provided for user " + user.getName());
            return;
        }

        List<TestingCentre> testingCentres = testingCenterSelectionStrategy.selectTestingCenters(user, testingCentreMap, N);

        // Assumption : Atleast one testing center is always present
        for(TestingCentre tc : testingCentres){
            tc.addToWaitList(user);
            user.getWaitingIn().add(tc);
        }
    }

    public void provideKit(String testCenterId, int count) {
        TestingCentre testingCentre = testingCentreMap.get(testCenterId);
        if(testingCentre==null){
            throw new IllegalArgumentException("Invalid Test Centre ID " + testCenterId);
        }
        testingCentre.receiveKit(count);
    }

    public TestingCentre getTestCenter(String tcId) {
        return testingCentreMap.get(tcId);
    }

    public void printAll() {
        System.out.println("Listing all available test centres :");
        for(String id : testingCentreMap.keySet()){
            TestingCentre tc = testingCentreMap.get(id);
            StringBuilder waitingUsers = new StringBuilder("[");
            for(String s : tc.getAllWaitingUsers()){
                waitingUsers.append(s).append(", ");
            }
            waitingUsers.append("]");
            String output = String.format("%20s\t%10s\t%7s\t%s\t%d",
                    tc.getId(), tc.getName(),tc.getLocation(), waitingUsers, tc.getStock());
            System.out.println(output);
        }
    }

    public void printTestCentre(String id) {
        TestingCentre tc = testingCentreMap.get(id);
        StringBuilder waitingUsers = new StringBuilder("[");
        for(String s : tc.getAllWaitingUsers()){
            waitingUsers.append(s).append(", ");
        }
        waitingUsers.append("]");
        String output = String.format("%20s\t%10s\t%7s\t%s\t%d",
                tc.getId(), tc.getName(),tc.getLocation(), waitingUsers, tc.getStock());
        System.out.println(output);
    }
}
