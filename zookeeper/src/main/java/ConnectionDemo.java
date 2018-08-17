import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ConnectionDemo {

    public static void main(String[] args) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper("10.16.0.112:2181,10.16.0.113:2181,10.16.0.110:2181", 4000, (watchedEvent)->{
                if(watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected){
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            System.out.println(zooKeeper.getState());

            //创建节点
            zooKeeper.create("/zk-persist-hxb","0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            Stat stat = new Stat();
            byte[] bytes = zooKeeper.getData("/zk-persist-hxb",null,stat);
            System.out.println(new String(bytes));

            zooKeeper.setData("/zk-persist-hxb","1".getBytes(),stat.getVersion());
            byte[] bytes1 = zooKeeper.getData("/zk-persist-hxb",null,stat);
            System.out.println(new String(bytes1));

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
