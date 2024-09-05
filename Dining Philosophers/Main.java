import java.util.concurrent.Semaphore;

public class Main
{
    public static void main( String[] args )
    {
        Semaphore[] chopsticks = new Semaphore[5];
        Philosopher[] philosophers = new Philosopher[5];
        
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Semaphore(1);
        }
        
        for (int i = 0; i < philosophers.length; i++) {
            Semaphore rightChop = chopsticks[i];
            Semaphore leftChop = chopsticks[(i+1) % philosophers.length];
            philosophers[i] = new Philosopher(i, rightChop, leftChop);
        }
        
        Thread[] philoThreads = new Thread[5];
        for (int i = 0; i < philoThreads.length; i++) {
            philoThreads[i] = new Thread(philosophers[i]);
            philoThreads[i].start();
        }

        try {
            for (int i = 0; i < philoThreads.length; i++) {
                philoThreads[i].join();
            }
        } catch (Exception e) {}
    }
}