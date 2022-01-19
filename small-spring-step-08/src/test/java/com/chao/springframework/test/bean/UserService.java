package com.chao.springframework.test.bean;

import com.chao.springframework.beans.BeansException;
import com.chao.springframework.beans.factory.BeanClassLoaderAware;
import com.chao.springframework.beans.factory.BeanFactory;
import com.chao.springframework.beans.factory.BeanFactoryAware;
import com.chao.springframework.beans.factory.BeanNameAware;
import com.chao.springframework.context.ApplicationContext;
import com.chao.springframework.context.ApplicationContextAware;

public class UserService implements BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    private String uId;
    private String companyName;
    private String location;
    private UserDao userDao;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("ClassLoader: " + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("Bean Name is: " + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationcontext) {
        this.applicationContext = applicationcontext;
    }

    public String queryUserInfo() {
        return userDao.queryUserName(uId) + "," + companyName + "," + location;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
