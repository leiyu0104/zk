import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Author: ly
 * @Date: 2019/6/27 22:15
 */
public class Buy {
    public static void main(String[] args){

        /*
        定义重试策略：等待两秒，重试10次
         */
        final RetryPolicy policy =  new ExponentialBackoffRetry(2000,10);
        /*
         创建客户端向zookeeper请求锁
         connectString:zookeeper地址
         retryPolicy:重试策略
         */
        CuratorFramework curatorFramework=CuratorFrameworkFactory.builder().connectString("127.0.0.1").retryPolicy(policy).build();
        //启用
        curatorFramework.start();

        //获取zookeeper锁信息
        final InterProcessMutex mutex=new InterProcessMutex(curatorFramework,"/myMutex");

        /*
        创建8个线程模拟客户端并发访问
         */
        for(int i=0;i<=8;i++){
            new Thread(new Runnable() {
                public void run() {
                    try {
                        //请求锁资源，如果没有获得，执行重试策略
                        mutex.acquire();
                        //开始访问共享资源
                        Client.buy();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        try {
                            //归还锁
                            mutex.release();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
