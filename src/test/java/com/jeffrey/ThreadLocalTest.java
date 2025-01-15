package com.jeffrey;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocalSetAndGet(){
        ThreadLocal tl = new ThreadLocal();
        new Thread(()->{
            tl.set("Jeffrey");
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
        },"thread-local1").start();
        new Thread(()->{
            tl.set("Gina");
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
        },"thread-local2").start();

    }
}
