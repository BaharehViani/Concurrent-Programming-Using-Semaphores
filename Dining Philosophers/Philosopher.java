import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Philosopher implements Runnable {
    
    private int id;
    private Semaphore rightChop;
    private Semaphore leftChop;
    private double previous;

    public Philosopher(int id, Semaphore rightChop, Semaphore leftChop) {
        this.id = id;
        this.rightChop = rightChop;
        this.leftChop = leftChop;
        previous = Math.random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (previous >= 0.5) {
                    eat();
                    think();
                    eat();
                    previous = 0;
                } else {
                    think();
                    eat();
                    think();
                    previous = 1;
                }
            }
        } catch (Exception e) {}
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + (id+1) + " started thinking");
        Thread.sleep((long) (Math.random()*5000));
        System.out.println("Philosopher " + (id+1) + " finished thinking");
    }

    private void eat() throws InterruptedException {
        rightChop.acquire();
        boolean acquired = leftChop.tryAcquire(3, TimeUnit.SECONDS);
        if (!acquired) {
            rightChop.release();
            return;
        }
        System.out.println("Philosopher " + (id+1) + " started eating...");
        Thread.sleep((long) (Math.random()*5000));
        System.out.println("Philosopher " + (id+1) + " finished eating...");
        rightChop.release();
        leftChop.release();
    }
}