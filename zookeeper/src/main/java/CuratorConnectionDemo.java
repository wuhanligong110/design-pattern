import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorConnectionDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.
                builder().connectString("10.16.0.112:2181,10.16.0.113:2181,10.16.0.110:2181").
                sessionTimeoutMs(4000).retryPolicy(new ExponentialBackoffRetry(1000,3)).
                namespace("curator").build();
        curatorFramework.start();

        curatorFramework.create().creatingParentsIfNeeded().
                withMode(CreateMode.PERSISTENT).
                forPath("/hxb/node1","1".getBytes());

        Stat stat = new Stat();
        byte[] btyes = curatorFramework.getData().storingStatIn(stat).forPath("/hxb/node1");
        System.out.println(new String(btyes));
        curatorFramework.setData().withVersion(stat.getVersion()).forPath("/hxb/node1","xx".getBytes());
        btyes = curatorFramework.getData().storingStatIn(stat).forPath("/hxb/node1");
        System.out.println(new String(btyes));
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/hxb/node1");
        curatorFramework.close();
    }
}
