package com.noweli.philosophers;

import java.util.concurrent.Semaphore;

public class Table {
    public static void main(String[] args) {
        Semaphore[] forks = createForks(5);
        Philosopher[] philosophers = createPhilosophers(5, forks);

        for(Philosopher philosopher : philosophers)
            philosopher.start();
    }


    //Static methods used to create forks and philosophers inside main method
    public static Semaphore[] createForks(int howMany) {
        Semaphore[] forks = new Semaphore[howMany];

        for (int i = 0; i < howMany; i++)
            forks[i] = new Semaphore(1);

        return forks;
    }

    public static Philosopher[] createPhilosophers(int howMany, Semaphore[] semaphores) {
        Philosopher[] philosophers = new Philosopher[howMany];

        for (int i = 0; i < howMany; i++) {
            if (i != (howMany - 1))
                philosophers[i] = new Philosopher(semaphores[i], semaphores[i + 1], "Philosopher " + (i + 1));
            else
                philosophers[i] = new Philosopher(semaphores[i], semaphores[0], "Philosopher " + (i + 1));
        }

        return philosophers;
    }
}
