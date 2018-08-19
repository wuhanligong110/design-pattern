import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class DistributeLock implements Lock,Watcher {

    private ZooKeeper zk = null;
    private String ROOT_LOCK = "/locks";
    private String WAIT_LOCK;
    private String CURRENT_LOCK;

    private CountDownLatch countDownLatch;

    public DistributeLock() {
        try {
            zk = new ZooKeeper("10.16.0.113:2181,10.16.0.112:2181,10.16.0.110:2181",4000,this);
            Stat stat = zk.exists(ROOT_LOCK,false);
            if(stat == null){
                zk.create(ROOT_LOCK,"0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lock() {
        if(this.tryLock()){
            System.out.println(Thread.currentThread().getName()+"->"+CURRENT_LOCK+":获取锁成功");
            return;
        }
        try {
            waitForLock(WAIT_LOCK);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean waitForLock(String wait_lock) throws KeeperException, InterruptedException {
        Stat stat = zk.exists(wait_lock,true);
        if(stat != null){
            System.out.println(Thread.currentThread().getName()+":等待锁"+wait_lock+"释放");
            countDownLatch = new CountDownLatch(1);
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName()+"->"+CURRENT_LOCK+":获取锁成功");
        }
        return true;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            CURRENT_LOCK = zk.create(ROOT_LOCK+"/","0".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName()+"->"+CURRENT_LOCK+",尝试竞争锁！");
            List<String> children = zk.getChildren(ROOT_LOCK,false);
            SortedSet<String> sortedSet = new TreeSet();
            for(String child : children){
                sortedSet.add(ROOT_LOCK + "/" + child);
            }
            String firstNode = sortedSet.first();
            if(firstNode.equals(CURRENT_LOCK)){
                return true;
            }
            SortedSet<String> lessThenMe = ((TreeSet<String>) sortedSet).headSet(CURRENT_LOCK);
            if(lessThenMe != null){
                WAIT_LOCK = lessThenMe.last();
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        System.out.println(Thread.currentThread().getName()+"->释放锁"+CURRENT_LOCK);
        try {
            zk.delete(CURRENT_LOCK,-1);
            CURRENT_LOCK=null;
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(this.countDownLatch != null){
            countDownLatch.countDown();
        }
    }
}
