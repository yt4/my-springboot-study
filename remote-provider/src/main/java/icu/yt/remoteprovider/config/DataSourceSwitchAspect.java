package icu.yt.remoteprovider.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author yt
 * @date 2022/2/22 10:13
 * 功能说明 用来设置数据源的AOP
 */
@Aspect
@Component
public class DataSourceSwitchAspect {

    /**
     * 使用@ReadDB注解修饰的方法或service层以select、find作为开头的方法会切换使用Slave数据源
     */
    @Pointcut("@annotation(icu.yt.remoteprovider.config.ReadDB) " +
            "|| execution(* icu.yt.remoteprovider.service.*.select*(..)) " +
            "|| execution(* icu.yt.remoteprovider.service..*.find*(..))")
    public void readPointcut() {

    }

    /**
     * 使用@WriteDB注解修饰的方法或service层以下列关键字为开头的方法会切换使用Master数据源
     */
    @Pointcut("@annotation(icu.yt.remoteprovider.config.WriteDB) " +
            "|| execution(* icu.yt.remoteprovider.service..*.save*(..)) " +
            "|| execution(* icu.yt.remoteprovider.service..*.add*(..)) " +
            "|| execution(* icu.yt.remoteprovider.service..*.update*(..)) " +
            "|| execution(* icu.yt.remoteprovider.service..*.edit*(..)) " +
            "|| execution(* icu.yt.remoteprovider.service.*.delete*(..)) " +
            "|| execution(* icu.yt.remoteprovider.service.*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.switchSlave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.switchMaster();
    }
}
