package com.noweli.philosophers;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Philosopher extends Thread{

    private Semaphore leftFork;
    private Semaphore rightFork;
    private Random random;

    private boolean hungry;

    public Philosopher(Semaphore leftFork, Semaphore rightFork, String name){
        this.leftFork = leftFork;
        this.rightFork = rightFork;

        setName(name);
        random = new Random();
        hungry = false;
    }

    @Override
    public void run() {
        System.out.println(getName() + " started thinking");
        while(true){
            sleeper(3);
            wannaEat();

            if(hungry){
                System.out.println(getName() + " is waiting for forks");
                acquireEatAndRelease();
            }
        }
    }

    private void wannaEat(){
        hungry = random.nextBoolean();
    }

    //Method to quickly call sleep without try-catch block
    private void sleeper(double seconds){
        try {
            sleep((long)seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //Acquires or goes into queue
    private void acquireForks(){
        try {
            leftFork.acquire();
            rightFork.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //Release forks
    private synchronized void releaseForks(){
        leftFork.release();
        System.out.println(getName() + " released left fork");
        rightFork.release();
        System.out.println(getName() + " released right fork");
    }

    //Eats for 2 seconds and releases Semaphore permit
    private void eatAndRelease(){
        System.out.println(getName() + " is eating now");
        sleeper(2);

        releaseForks();
    }

    private void acquireEatAndRelease(){
        acquireForks();
        eatAndRelease();
    }
}
