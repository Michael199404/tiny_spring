package com.chao.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanException;
import com.chao.springframework.beans.BeansException;
import com.chao.springframework.beans.factory.FactoryBean;
import com.chao.springframework.beans.factory.config.BeanDefinition;
import com.chao.springframework.beans.factory.config.BeanPostProcessor;
import com.chao.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.chao.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * AbstractBeanFactory 继承实现了 SingletonBeanRegistry 的 DefaultSingletonBeanRegistry，
 * 这样，AbstractBeanFactory 抽象类就具备了单例 Bean 的注册功能
 *
 * 接下来很重要的一点是关于接口 BeanFactory 的实现，在方法 getBean 的实现过程中可以看到，主要是对单例 Bean 对象
 * 的获取以及在获取不到时需要拿到 Bean 的定义做 Bean 实例化操作
 *
 * getBean 并没有自身去实现这些方法，而是只定义了调用过程以及提供了抽象方法，由实现此抽象类的其他类做相应实现
 */

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return (T) getBean(name);
    }

    // 泛型方法，是在调用方法的时候指明泛型的具体类型
    protected <T> T doGetBean(final String name, final Object[] args) {
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    /**
     * 我们在得到 bean 的实例之后要做的第一步就是调用 getObjectForBeanInstance() 方法
     * 检测当前的 bean 是否是 FactoryBean 类型的 bean，如果是，那么需要调用该 bean 对应
     * 的 FactoryBean 实例中的 getObject() 作为返回值
     */
    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        // 现在我们有了个 bean 实例，这个实例可能会是正常的 bean 或者是 FactoryBean
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        // 尝试从缓存中加载 bean
        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    protected abstract BeanDefinition getBeanDefinition(String name);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * Return the list of BeanPostProcessors that will get applied to
     * beans created with this factory
     *
     * @return
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
}
