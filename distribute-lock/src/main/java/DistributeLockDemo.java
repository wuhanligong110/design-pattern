import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class DistributeLockDemo {
    public static void main(String[] args) throws IOException {
        int count = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        for(int i = 0; i < count; i++){
            new Thread(()->{
                try {
                    cyclicBarrier.await();
                    DistributeLock distributeLock = new DistributeLock();
                    distributeLock.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },"Thread-"+i).start();
        }
        System.in.read();
    }
}
