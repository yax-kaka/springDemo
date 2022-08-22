package test.demo.service.impl;

import test.demo.service.AopWash;

public class WomanWash implements AopWash {
    @Override
    public void takeAWash() {
        System.out.println("女性怎么洗的？我不到啊");
    }
}
