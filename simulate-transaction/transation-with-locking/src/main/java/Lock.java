public class Lock {
    public static boolean isCustomerTableLocked = false;
    private Thread currentWriteLockOwner;

    public synchronized void acquireExclusiveLock() throws InterruptedException {
        while(isCustomerTableLocked) wait();
        isCustomerTableLocked = true;
        currentWriteLockOwner = Thread.currentThread();
    }

    public synchronized void releaseExclusiveLock(){
        isCustomerTableLocked = false;
        currentWriteLockOwner = null;
        notifyAll();
    }
}
