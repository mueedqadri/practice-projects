import java.io.IOException;


public class Transaction
{
    public static void main(String[] args) throws IOException {
        Logging log = new Logging();
        Lock lock = new Lock();
        Runnable t1 = new T1( log, lock);
        Runnable t2 = new T2( log, lock);
        Runnable t3 = new T3( log, lock);
        Thread thread1 = new Thread(t1);
        Thread thread2 = new Thread(t2);
        Thread thread3 = new Thread(t3);
        thread1.start();
        thread2.start();
        thread3.start();
    }
}