import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BoundedBuffer {
    
    private Queue<Integer> buffer;
    private Semaphore mutex;
    private Semaphore empty;
    private Semaphore full;

    public BoundedBuffer() {
        buffer = new LinkedList<>();
        mutex = new Semaphore(1);
        empty = new Semaphore(5);
        full = new Semaphore(0);
    }
    
    public void produce(int value) throws InterruptedException {
        empty.acquire();
        mutex.acquire();
        buffer.add(value);
        System.out.println("Producer produced: " + value);
        mutex.release();
        full.release();
    }

    public void consume() throws InterruptedException {
        full.acquire();
        mutex.acquire();
        int item = buffer.poll();
        System.out.println("Consumer consumed: " + item);
        mutex.release();
        empty.release();
    }
}