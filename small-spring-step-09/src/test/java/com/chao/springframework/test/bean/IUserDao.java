package com.chao.springframework.test.bean;

/**
 * 删掉 UserDao，定义一个 IUserDao 接口，之所以这样做是为了通过 FactoryBean
 * 做一个自定义对象的代理操作
 */
public interface IUserDao {

    String queryUserName(String uId);

}
