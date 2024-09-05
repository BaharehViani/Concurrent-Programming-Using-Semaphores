public class Main
{
    public static void main( String[] args )
    {
        BoundedBuffer sharedBuffer = new BoundedBuffer();
        
        Thread producerThread = new Thread(new Producer(sharedBuffer));
        Thread consumerThread = new Thread(new Consumer(sharedBuffer));
         		
        producerThread.start();
        consumerThread.start(); 

        try {
            producerThread.join();
            consumerThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}