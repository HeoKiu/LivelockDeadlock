package org.mipt;


import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Locks {

    public static void deadlock() {
        final Object resource1 = "resource1";
        final Object resource2 = "resource2";
        Thread t1 = new Thread() {
            public void run() {
                //Lock resource 1
                synchronized(resource1){
                    System.out.println("Thread 1: locked resource 1");
                    try{
                        Thread.sleep(50);
                    } catch (InterruptedException e) {}

                    //Now wait 'till we can get a lock on resource 2
                    synchronized(resource2){
                        System.out.println("Thread 1: locked resource 2");
                    }
                }
            }
        };


        Thread t2 = new Thread(){
            public void run(){
                synchronized(resource2){
                    System.out.println("Thread 2: locked resource 2");

                    try{
                        Thread.sleep(50);
                    } catch (InterruptedException e){}

                    synchronized(resource1){
                        System.out.println("Thread 2: locked resource 1");
                    }
                }
            }
        };

        t1.start();
        t2.start();
    }

    /**
     * This method causes livelock. Threads are not blocked but they are too busy responding to
     * requests of each other and cannot make progress because of that
     */
    public static void livelock() throws InterruptedException{
        LivelockExample livelock = new LivelockExample();
        new Thread(livelock::operation1, "T1").start();
        Thread.sleep(1);
        new Thread(livelock::operation2, "T2").start();
        Thread.sleep(2000);

    }

}