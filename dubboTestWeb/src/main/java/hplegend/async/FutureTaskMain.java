package hplegend.async;

import java.util.concurrent.ExecutionException;

/**
 * @author hp.he
 * @date 2019/8/28 17:54
 */
public class FutureTaskMain {

    public static void main(String []args) throws ExecutionException, InterruptedException {
    /*    FutureTaskImplement futureTaskImplement = new FutureTaskImplement();
        futureTaskImplement.runnableGetResult();
        futureTaskImplement.callableGetResult();
        futureTaskImplement.variableThreadRet();*/

        ListenableFutureImpl listenableFuture = new ListenableFutureImpl();
        listenableFuture.listenableFuture();
        System.out.println("werwerwe");
    }

}
