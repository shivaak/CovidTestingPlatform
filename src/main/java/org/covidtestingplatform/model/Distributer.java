package org.covidtestingplatform.model;

public class Distributer implements Runnable {

    private WaitingQueue wq;

    public Distributer(WaitingQueue wq) {
        this.wq = wq;
    }

    @Override
    public void run() {
        while(true){
            try {
                wq.serveUser();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
