package com.chao.springframework.test.dynaProxy;

public class GunDog implements Dog {

    @Override
    public void info() {
        System.out.println("我是一只猎狗");
    }

    @Override
    public void run() {
        System.out.println("我迅速奔跑");
    }
}
