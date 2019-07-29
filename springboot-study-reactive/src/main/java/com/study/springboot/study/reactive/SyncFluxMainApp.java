package com.study.springboot.study.reactive;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.function.Consumer;

/**
 * FLUX实例
 */
public class SyncFluxMainApp {
    public static void main(String[] args) throws InterruptedException {
        printThread("Hello Flux");
        Flux<String> abc = Flux.just("A", "B", "C");

//        subscribe(abc);
//        publishOn(abc);
//        publishOnStreamRunner(abc);
//        publishComplete(abc);
//        publishMap(abc);
//        publishBackpressure(abc);
        publishToPublisher(abc);
    }

    /**
     * 1. 当前线程就是main线程，会直接输出，也就是非阻塞序列
     *
     * @param flux
     */
    public static void subscribe(Flux flux) {
        flux
                .subscribe(SyncFluxMainApp::printThread);
    }

    /**
     * 2. publish，切换线程池
     * 把当前的flux发布到flux中去
     *
     * @param flux
     */
    public static void publishOn(Flux flux) {
        flux
                .publishOn(Schedulers.elastic()) //发布到线程池中
                .subscribe(SyncFluxMainApp::printThread)
        ; //在线程池中订阅，并执行这个方法
    }

    /**
     * 3. 跟completableFutre做对比，结束的时候打印会出现错误，主要是主线程提前结束了
     * @param flux
     */
    public static void publishOnStreamRunner(Flux flux) {
        /**
         * 类似于CompletableFuture
         *      // main -> submit -> ...
         *          // sub-thread: F1 -> F2 -> F3
         *      CompletableFuture.runAsync(super::loadConfigurations)
         *                 .thenRun(super::loadUsers)
         *                 .thenRun(super::loadOrders)
         *                 .whenComplete((result, throwable) -> {
         *                     System.out.println("[线程：" + Thread.currentThread().getName() + "] 完成");
         *                 })
         *                 .exceptionally(throwable -> {
         *                     System.out.println("[线程：" + Thread.currentThread().getName() + "] 异常");
         *                     return null;
         *                 })
         *                 .join();
         */
        flux
                .publishOn(Schedulers.elastic()) //发布到线程池中
                .subscribe(  //subscribe的重载方法
                        SyncFluxMainApp::printThread,  // 数据消费 trycatch当中的try
                        SyncFluxMainApp::printThread,  // 异常处理 trycatch当中的catch
                        ()->{printThread("完成处理");})
        ;
    }

    /**
     * 3.1 完成操作
     * @param flux
     */
    public static void publishComplete(Flux flux) throws InterruptedException {
        flux
                .publishOn(Schedulers.elastic()) //发布到线程池中
                .map(value -> "+" + value) // "A" -> "+A"
                .subscribe(  //subscribe的重载方法
                        SyncFluxMainApp::printThread,  // 数据消费 trycatch当中的try
                        SyncFluxMainApp::printThread,  // 异常处理 trycatch当中的catch
                        ()->{printThread("完成处理");})
        ;
        Thread.sleep(3000L);
    }

    /**
     * 4.map，对flux中的每一个进行操作
     * @param flux
     */
    public static void publishMap(Flux flux) {
        flux
                .publishOn(Schedulers.elastic()) //发布到线程池中
                .map(value -> "+" + value) // "A" -> "+A"
                .subscribe(  //subscribe的重载方法
                        SyncFluxMainApp::printThread,  // 数据消费 trycatch当中的try
                        SyncFluxMainApp::printThread,  // 异常处理 trycatch当中的catch
                        ()->{printThread("完成处理");})
        ;
    }

    /**
     * 5 背压操作
     *
     * @param flux
     */
    public static void publishBackpressure(Flux flux) {
        flux
//                .publishOn(Schedulers.elastic()) //发布到线程池中
                .map(value -> "+" + value) // "A" -> "+A"
                .subscribe(  //subscribe的重载方法
                        SyncFluxMainApp::printThread,  // ==》 onNext
                        SyncFluxMainApp::printThread,  // ==》 onError
                        () -> {
                            printThread("完成处理");  //==》 onComplete
                        },
                        //subecription->{subscription.request(Integer.MAX_VALUE);} //bug了，idea不靠谱
                        new Consumer<Subscription>() {  // ==》 onsubscribe
                            @Override
                            public void accept(Subscription subscription) {
                                //subscription.request(3); // n:向上游请求的元素数量
                                subscription.cancel();//取消上游传输数据到下游
                                subscription.request(3); // 这条语句如果在下面就不会获得任何数据
                            }
                        })
        ;
    }

    /**
     * 6 publisher的调用
     *
     * @param flux
     */
    public static void publishToPublisher(Flux flux) {
        flux
//                .publishOn(Schedulers.elastic()) //发布到线程池中
                .map(value -> "+" + value) // "A" -> "+A"
                .subscribe(new Subscriber<String>() {
                    private Subscription subscription;

                    int counter = 0;

                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription = s;
                        subscription.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        if(counter == 2){
                            throw new RuntimeException("数量到达2");
                        }
                        printThread(s);
                        counter++;
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        printThread(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        printThread("完成操作");
                    }
                });
        ;
    }

    public static void printThread(Object object) {
        String threadName = Thread.currentThread().getName();
        System.out.println("[线程: " + threadName + "] ->" + object);
    }
}
