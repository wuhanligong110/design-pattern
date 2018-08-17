import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorWatchDemo {

    public static void main(String[] args) throws Exception {

        CuratorFramework curatorFramework = CuratorFrameworkFactory.
                builder().connectString("10.16.0.112:2181,10.16.0.113:2181,10.16.0.110:2181").
                sessionTimeoutMs(4000).retryPolicy(new ExponentialBackoffRetry(1000,3)).
                namespace("curator").build();
        curatorFramework.start();

        //监听当前节点的创建、修改
        //addListenerWithNodeCache(curatorFramework,"/hxb");

        //监听当前目录子节点的增加、修改、删除的事件
        //addListenerWithPathChildCache(curatorFramework,"/hxb");

        //监听当前目录所有节点
        addListenerWithTreeCache(curatorFramework,"/hxb");

        System.in.read();

    }

    public static void addListenerWithTreeCache(CuratorFramework curatorFramework,String path) throws Exception {
        TreeCache treeCache = new TreeCache(curatorFramework,path);
        TreeCacheListener treeCacheListener = (client,treeCacheEvent) -> {
                System.out.println(treeCacheEvent.getType() + "->" + treeCacheEvent.getData().getPath());
        };
        treeCache.getListenable().addListener(treeCacheListener);
        treeCache.start();
    }

    public static void addListenerWithPathChildCache(CuratorFramework curatorFramework,String path) throws Exception {
        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework,path,true);
        PathChildrenCacheListener childrenCacheListener = (client, treeCacheEvent) -> {
            System.out.println(treeCacheEvent.getType() + "->" + treeCacheEvent.getData().getPath());
        };
        childrenCache.getListenable().addListener(childrenCacheListener);
        childrenCache.start();
    }

    public static void addListenerWithNodeCache(CuratorFramework curatorFramework,String path) throws Exception {
        NodeCache nodeCache = new NodeCache(curatorFramework,path,false);
        NodeCacheListener nodeCacheListener = () -> {
            System.out.println("Receive Event:"+nodeCache.getCurrentData().getPath());
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }
}
