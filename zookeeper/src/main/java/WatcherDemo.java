import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class WatcherDemo {

    public static void main(String[] args) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper("10.16.0.112:2181,10.16.0.113:2181,10.16.0.110:2181", 4000, (watchedEvent)->{
                System.out.println("默认事件："+watchedEvent.getType());
                if(watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected){
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();

            //创建节点
            zooKeeper.create("/zk-persist-hxb","0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            /*Stat stat = zooKeeper.exists("/zk-persist-hxb",watchedEvent -> {
                System.out.println(watchedEvent.getType()+"->"+watchedEvent.getPath());
                try {
                    zooKeeper.exists("/zk-persist-hxb",true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });*/

            Stat stat = new Stat();
            zooKeeper.getData("/zk-persist-hxb",watchedEvent -> {
                System.out.println(watchedEvent.getType()+"->"+watchedEvent.getPath());
                try {
                    zooKeeper.exists("/zk-persist-hxb",true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },stat);

            stat = zooKeeper.setData("/zk-persist-hxb","1".getBytes(),stat.getVersion());

            Thread.sleep(1000);

            zooKeeper.delete("/zk-persist-hxb",stat.getVersion());

            zooKeeper.close();
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
