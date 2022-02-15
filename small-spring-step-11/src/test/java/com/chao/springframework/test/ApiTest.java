package com.chao.springframework.test;

import com.chao.springframework.aop.MethodMatcher;
import com.chao.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.chao.springframework.aop.framework.ReflectiveMethodInvocation;
import com.chao.springframework.test.bean.IUserService;
import com.chao.springframework.test.bean.UserService;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ApiTest {

    @Test
    public void test_proxy_method() {
        // 目标对象（可以替换成任何目标对象）
        UserService targetObj = new UserService();

        // AOP代理
        IUserService proxy = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetObj.getClass().getInterfaces(), new InvocationHandler() {
            // 方法匹配
            MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* com.chao.springframework.test.bean.UserService.*(..))");

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (methodMatcher.matches(method, targetObj.getClass())) {
                    org.aopalliance.intercept.MethodInterceptor methodInterceptor = invocation -> {
                        long start = System.currentTimeMillis();
                        try {
                            return invocation.proceed();
                        } finally {
                            System.out.println("监控 - Begin By AOP");
                            System.out.println("方法名称：" + invocation.getMethod().getName());
                            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
                            System.out.println("监控 - End\r\n");
                        }
                    };
                    // 反射调用
                    return methodInterceptor.invoke(new ReflectiveMethodInvocation(targetObj, method, args));
                }
                return method.invoke(targetObj, args);
            }
        });
        String result = proxy.queryUserInfo();
        System.out.println("测试结果:" + result);
    }
    
    @Test
    public void test_aop() throws NoSuchMethodException {
        // 切入点，用来表示需要拦截的方法
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.chao.springframework.test.bean.UserService.*(..))");

        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");

        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
    }

    /**
     * Enhancer 是一个字节码增强器，可以用来为无接口的类创建代理。它会根据某个给定的类创建子类
     * @param args
     */
    public static void main(final String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Car.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("before");
                Object res = methodProxy.invokeSuper(o, objects);
                System.out.println("after");
                return res;
            }
        });
        Car car = (Car)enhancer.create();
        car.print();
    }

    static class Car {
        void print() {
            System.out.println("I am a car");
        }
    }

}
