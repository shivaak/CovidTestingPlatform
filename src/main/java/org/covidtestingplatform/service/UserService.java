package org.covidtestingplatform.service;

import org.covidtestingplatform.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    private static final UserService userService = new UserService();

    private Map<String, User> userMap = new HashMap<>();

    private UserService(){

    }

    public static UserService get(){
        return userService;
    }

    public void addUser(User user){
        if(userMap.containsKey(user.getId())){
            throw new IllegalArgumentException("User alread exist with ID " + user.getId());
        }
        userMap.put(user.getId(), user);
    }

    public User getUser(String id){
        return userMap.get(id);
    }

    public void printAll() {
        System.out.println("Listing all available users :");
        for(String id : userMap.keySet()){
            User user = userMap.get(id);
            String output = String.format("%20s\t%10s\t%7s\t%10s\t%s\t%s",
                    user.getId(), user.getName(),user.getLocation(),user.getWaitingIn(), user.isVaccinationProvided(), user.getVaccinatedFrom());
            System.out.println(output);
        }
    }
}
