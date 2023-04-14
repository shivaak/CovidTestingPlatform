package org.covidtestingplatform.logic;

import org.covidtestingplatform.model.TestingCentre;
import org.covidtestingplatform.model.User;

import java.util.*;

public class NClosestTestingCenter implements TestingCenterSelectionStrategy{
    @Override
    public List<TestingCentre> selectTestingCenters(User user, Map<String, TestingCentre> testingCentreMap, int numberOfCentres) {
        PriorityQueue<TestingCentre> heap = new PriorityQueue<>((p1, p2)->{
            /*// Copied this formula from Internet; This formula simplifies the calculation of Euclidean distance sqrt(pow(x2-x1, 2) + pow(y2-y1, 2))
            return p2.getLocation().getX() * p2.getLocation().getX() + p2.getLocation().getY() * p2.getLocation().getY()
                    - p1.getLocation().getX() * p1.getLocation().getX() - p1.getLocation().getY() * p1.getLocation().getY();
*/
            int ux = user.getLocation().getX();
            int uy = user.getLocation().getY();

            int x1= p1.getLocation().getX();
            int y1= p1.getLocation().getY();
            int x2= p2.getLocation().getX();
            int y2= p2.getLocation().getY();

            Double distanceOfP1FromUser = Math.sqrt(Math.pow(x1-ux, 2) + Math.pow(y1-uy, 2));
            Double distanceOfP2FromUser = Math.sqrt(Math.pow(x2-ux, 2) + Math.pow(y2-uy, 2));
            return distanceOfP2FromUser.compareTo(distanceOfP1FromUser);
        });

        for(String tcId : testingCentreMap.keySet()){
            heap.add(testingCentreMap.get(tcId));
            if(heap.size()>numberOfCentres){
                heap.poll();
            }
        }

        ArrayList<TestingCentre> result = new ArrayList<>();
        while(!heap.isEmpty()){
            result.add(heap.poll());
        }
        ArrayList<TestingCentre> reverseResult = new ArrayList<>();
        for (int i = result.size() - 1; i >= 0; i--) {
            // Append the elements in reverse order
            reverseResult.add(result.get(i));
        }

        return reverseResult;
    }
}
