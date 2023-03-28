import java.io.IOException;


public class Transaction
{
    public static void main(String[] args) throws IOException {
        Logging log = new Logging();
        Runnable t1 = new T1( log );
        Runnable t2 = new T2( log );
        Runnable t3 = new T3( log );
        Thread thread1 = new Thread(t1);
        Thread thread2 = new Thread(t2);
        Thread thread3 = new Thread(t3);
        thread1.start();
        thread2.start();
        thread3.start();
    }
}