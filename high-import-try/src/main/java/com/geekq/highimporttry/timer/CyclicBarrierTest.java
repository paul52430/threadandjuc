package com.geekq.highimporttry.timer;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author 邱润泽 bullock
 */
public class CyclicBarrierTest {


//    static CyclicBarrier c = new CyclicBarrier(2);
//
//    public static void main(String[] args) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(211111);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    c.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(1);
//            }
//        }).start();
//
//        try {
//            c.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (BrokenBarrierException e) {
//            e.printStackTrace();
//        }
//        System.out.println(2);
//    }


        static class PreTaskThread implements Runnable {
            private String task;
            private CyclicBarrier cyclicBarrier;

            public PreTaskThread(String task, CyclicBarrier cyclicBarrier) {
                this.task = task;
                this.cyclicBarrier = cyclicBarrier;
            }

            @Override
            public void run() {
                for (int i = 0; i < 4; i++) {
                    Random random = new Random();
                    try {
                        Thread.sleep(random.nextInt(1000));
                        System.out.println(String.format("关卡 %d 的任务 %s 完成", i, task));
                        cyclicBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }

            public static void main(String[] args) {
                CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> {
                    System.out.println("本关卡所有的前置任务完成，开始游戏... ...");
                });
                new Thread(new PreTaskThread("加载地图数据", cyclicBarrier)).start();
                new Thread(new PreTaskThread("加载人物模型", cyclicBarrier)).start();
                new Thread(new PreTaskThread("加载背景音乐", cyclicBarrier)).start();
                new Thread(new PreTaskThread("加载最新设备", cyclicBarrier)).start();
            }
        }

    }
