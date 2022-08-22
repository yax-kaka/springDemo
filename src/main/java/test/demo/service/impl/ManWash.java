package test.demo.service.impl;

import test.demo.service.AopWash;

public class ManWash implements AopWash {
    @Override
    public void takeAWash() {
        System.out.println("洗澡？洗完头冲一下水，搞定");
    }

}
