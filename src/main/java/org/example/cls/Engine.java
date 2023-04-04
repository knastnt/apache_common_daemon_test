package org.example.cls;

public class Engine {
    private boolean isStopped;
    public boolean isStopped() {
        System.out.println("isStopped");
        return isStopped;
    }

    public void stop() {
        System.out.println("stop");
        isStopped = true;
    }
}
