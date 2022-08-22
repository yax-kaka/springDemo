package test.demo.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 衣服切面
 */
@Aspect
@Component
public class DressAction {

    @Before("execution(* test.demo.service.AopWash.takeAWash())")
    public void underDress() {
        System.out.println("开洗之前，去衣");
    }

    @After("execution(* test.demo.service.AopWash.takeAWash())")
    public void wearDress() {
        System.out.println("洗完力，就穿个睡衣嗷");
    }
}
