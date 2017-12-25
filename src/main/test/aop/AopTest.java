package aop;


import org.smart4j.framework.HelperLoader;

/**
 * Created by daiwei on 2017/12/25.
 */
public class AopTest {
    public static void main(String[] args) {
        HelperLoader.init();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TargetObject targetObject=new TargetObject();
        targetObject.print();
    //    System.out.println("hhah");
    }
}
