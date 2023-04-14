package org.covidtestingplatform.model;

import lombok.*;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
public class TestingCentre {

    private String id;
    private String name;
    private Coordinates location;
    private Integer numberOfKits;
    private BlockingDeque<User> waitingList = new LinkedBlockingDeque<>();
    private  WaitingQueue wq;

    public TestingCentre(String name, Coordinates location){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.location = location;
        this.numberOfKits=2;

       wq = new WaitingQueue(name, waitingList, numberOfKits);

        Thread distributerThread = new Thread(new Distributer(wq));
        distributerThread.start();
    }

    public void addToWaitList(User user) {
        wq.addUser(user);
    }

    public void receiveKit(int count) {
        wq.receiveKit(count);
    }

    public ArrayList<String> getAllWaitingUsers() {
        return wq.getAllWaitingUsers();
    }

    public Integer getStock() {
        return wq.getNumberOfKits();
    }
}

class WaitingQueue {

    private ReentrantLock l = new ReentrantLock();
    private final Condition condition = l.newCondition();

    private BlockingDeque<User> waitingList;

    private Integer numberOfKits;

    private String name;

    public WaitingQueue(String name, BlockingDeque<User> waitingList, Integer numberOfKits) {
        this.waitingList = waitingList;
        this.numberOfKits = numberOfKits;
        this.name = name;
    }

    public void addUser(User user) {
        l.lock();
        waitingList.add(user);
        condition.signal();
        l.unlock();
    }

    public void receiveKit(int count) {
        l.lock();
        numberOfKits = numberOfKits + count;
        condition.signal();
        l.unlock();
    }


    public void serveUser() throws InterruptedException {
        l.lock();
        while(waitingList.isEmpty() || numberOfKits<=0){
            if(numberOfKits<=0){
                System.out.println(String.format("Message from %s. Vaccination not in stock", name));
            }else{
                System.out.println(String.format("Message from %s. No one in waiting list", name));
            }
            condition.await();
        }
        if(waitingList.peek()==null){
            System.out.println("user is null");
        }
        User user = waitingList.poll();
        if(user==null){
            System.out.println("user is null");
        }
        boolean success = user.vaccinate(name);
        if(success){
            numberOfKits--;
            System.out.println(String.format("Number of kits remaining in %s is %d",name, numberOfKits));
        }
        condition.signal();
        l.unlock();
    }

    public BlockingDeque<User> getWaitingList() {
        return waitingList;
    }

    public ArrayList<String> getAllWaitingUsers() {
        ArrayList<String> userList = new ArrayList<>();
        for (User u : waitingList) {
            userList.add(u.getId());
        }
        return userList;
    }

    public Integer getNumberOfKits() {
        return numberOfKits;
    }
}


