package hplegend.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hp.he
 * @date 2019/8/28 17:41
 */
public class FutureTaskImplement {


    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 8, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));


    /**
     * Runnable 接口获取返回值
     * 实际上 poolExecutor对于Runnable的接口是不返回值的，输入的参数，只能是参数的类型
     * 个人觉得，没有实际的意义
     */
    public void runnableGetResult() throws ExecutionException, InterruptedException {
        Future runnableRet = poolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("sdfd");
            }
        }, String.class);

        System.out.print(runnableRet.get());
    }


    /**
     * callAble接口可以直接拿到值，具体说：就是拿到线程的执行结果，在使用线程池技术下。
     * 如果我们不使用线程次，我们可以使用共享变量的方式，拿到线程的执行结果。见下面的variableThreadRet，需要一点点线程同步的知识
     */
    public void callableGetResult() throws ExecutionException, InterruptedException {
        Future runnableRet = poolExecutor.submit(new Callable<String>() {
            @Override
            public String call() {
                System.out.println("sdfd");
                return "dsdfsd";
            }
        });
        System.out.print(runnableRet.get());
    }


    public void variableThreadRet() {
        List<String> aa = new ArrayList<>();

        CountDownLatch latch = new CountDownLatch(2);
        new Thread() {
            @Override
            public void run() {
                aa.add("100");
                latch.countDown();
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                aa.add("200");
                latch.countDown();
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(aa);
    }

}
