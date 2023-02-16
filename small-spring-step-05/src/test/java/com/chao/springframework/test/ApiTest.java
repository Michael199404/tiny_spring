package com.chao.springframework.test;

import cn.hutool.core.io.IoUtil;
import com.chao.springframework.beans.PropertyValue;
import com.chao.springframework.beans.PropertyValues;
import com.chao.springframework.beans.factory.config.BeanDefinition;
import com.chao.springframework.beans.factory.config.BeanReference;
import com.chao.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.chao.springframework.beans.factory.support.xml.XmlBeanDefinitionReader;
import com.chao.springframework.core.io.DefaultResourceLoader;
import com.chao.springframework.core.io.Resource;
import com.chao.springframework.test.bean.UserDao;
import com.chao.springframework.test.bean.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApiTest {

    @Test
    public void test_BeanFactory() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.UserDao 注册
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 3.UserService 设置属性
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        // 4. UserService 注入 bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 4.UserService 获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

    private DefaultResourceLoader resourceLoader;

    @Before
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void test_classPath() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_file() throws IOException {
        Resource resource = resourceLoader.getResource("src/test/resources/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_url() throws IOException {
        Resource resource = resourceLoader.getResource("https://github.com/fuzhengwei/small-spring/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_xml() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3.获取Bean对象，调用方法
        Object userService = beanFactory.getBean("userService", UserService.class);

    }

    @Test
    public void compareJson() {
        String original = "{\"product\":\"MIPAY\",\"clientType\":\"WEB,WAP,APK,JSAPI,QR,AUTO_DEDUCT,B2C\",\"feeInfo\":[{\"minAmount\":100,\"maxAmount\":10000,\"feeModel\":\"BY_TRADE_RATE\",\"feeRate\":200,\"minFee\":100,\"maxFee\":2000,\"chargeType\":\"PAYEE\",\"chargeMerchantId\":\"\",\"feeInfoId\":1}],\"auditTimestamp\":\"1667210961161\",\"merchantId\":\"1000000319\",\"activeTime\":\"2022-10-31 18:22:00\",\"closeTime\":\"2022-11-05 18:21:00\",\"status\":\"1\"}";
        String modification = "{\"product\":\"WEIXIN\",\"clientType\":\"WEB,WAP,APK,JSAPI,QR,AUTO_DEDUCT,B2C\",\"feeInfo\":[{\"minAmount\":100,\"maxAmount\":10000,\"feeModel\":\"BY_TRADE_RATE1\",\"feeRate\":200,\"minFee\":100,\"maxFee\":2000,\"chargeType\":\"PAYEE\",\"chargeMerchantId\":\"\",\"feeInfoId\":1}],\"auditTimestamp\":\"1667210961161\",\"merchantId\":\"1000000319\",\"activeTime\":\"2022-10-31 18:22:00\",\"closeTime\":\"2022-11-05 18:21:00\",\"status\":\"1\"}";
        String result = compareTwoJson(original, modification);
        System.out.println(result);
    }

    /**
     * 遍历 json
     * 思路：如果是集合，必须是有序的且按照规则排好序的
     */
    public String compareTwoJson(String original, String modification) {
//        JSONObject originalJson = JSONObject.parseObject(original);
//        JSONObject modificationJson = JSONObject.parseObject(modification);
        JSONObject originalJson = JSONObject.fromObject(original);
        JSONObject modificationJson = JSONObject.fromObject(modification);
        // 递归遍历 json 对象的所有 key-value，将其封装为 path:value 格式进行比较
        Map<String, Object> oldMap = new LinkedHashMap<>();
        Map<String, Object> newMap = new LinkedHashMap<>();
        convertJsonToMap(originalJson, "", oldMap);
        convertJsonToMap(modificationJson, "", newMap);
        Map<String, Object> differentMap = compareMap(oldMap, newMap);
        return convertMapToJson(differentMap);
    }

    /**
     * 将 json 数据转换为 map 存储用于比较
     */
    private void convertJsonToMap(Object json, String root, Map<String, Object> resultMap) {
        if (json instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) json;
            Iterator iterator = jsonObject.keySet().iterator();
            while (iterator.hasNext()) {
                Object key = iterator.next();
                Object value = jsonObject.get(key);
                String newRoot = "".equals(root) ? key + "" : root + "." + key;
                if (value instanceof JSONObject || value instanceof JSONArray) {
                    convertJsonToMap(value, newRoot, resultMap);
                } else {
                    resultMap.put(newRoot, value);
                }
            }
        } else if (json instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) json;
            for (int i = 0; i < jsonArray.size(); i++) {
                Object value = jsonArray.get(i);
                String newRoot = "".equals(root) ? "[" + i + "]" : root + ".[" + i + "]";
                if (value instanceof JSONObject || value instanceof JSONArray) {
                    convertJsonToMap(value, newRoot, resultMap);
                } else {
                    resultMap.put(newRoot, value);
                }
            }
        }
    }

    /**
     * 比较两个 map，返回不同数据
     */
    private Map<String, Object> compareMap(Map<String, Object> oldMap, Map<String, Object> newMap) {
        // 遍历 newMap，将 newMap 的不同数据装进 oldMap，同时删除 oldMap 中与 newMap 相同的数据
        compareNewToOld(oldMap, newMap);
        compareOldToNew(oldMap);
        return oldMap;
    }

    /**
     * 将旧的有新的没有的数据封装数据结构在旧的里面
     * @param oldMap
     */
    private void compareOldToNew(Map<String, Object> oldMap) {
        //统一oldMap中newMap不存在的数据的数据结构，便于解析
        for (Iterator<Map.Entry<String, Object>> it = oldMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> item = it.next();
            String key = item.getKey();
            Object value = item.getValue();
            if (!(value instanceof Map)) {
                Map<String, Object> differenceMap = new HashMap<>();
                differenceMap.put("oldValue", value);
                differenceMap.put("newValue", "");
                oldMap.put(key, differenceMap);
            }
        }
    }

    /**
     * 将新的 map 和 旧的 map 比较，并将数据统一存在旧的里面
     * @param oldMap
     * @param newMap
     */
    private void compareNewToOld(Map<String, Object> oldMap, Map<String, Object> newMap) {
        for (Iterator<Map.Entry<String, Object>> it = newMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> item = it.next();
            String key = item.getKey();
            Object newValue = item.getValue();
            Map<String, Object> differenceMap = new HashMap<>();
            if (oldMap.containsKey(key)) {
                Object oldValue = oldMap.get(key);
                if (newValue.equals(oldValue)) {
                    oldMap.remove(key);
                    continue;
                } else {
                    differenceMap.put("oldValue", oldValue);
                    differenceMap.put("newValue", newValue);
                    oldMap.put(key, differenceMap);
                }
            } else {
                differenceMap.put("oldValue", "");
                differenceMap.put("newValue", newValue);
                oldMap.put(key, differenceMap);
            }
        }
    }

    @Test
    public void compareJson1() {
        String original = "{\"product\":\"MIPAY\",\"feeInfo\":[{\"minAmount\":100,\"feeModel\":\"BY_TRADE_RATE\"}]}";
        String modificy = "{\"product\":\"MIPAY\",\"feeInfo\":[{\"minAmount\":101,\"feeModel\":\"BY_TRADE_RATE\"}]}";
        String result = compareTwoJson(original, modificy);
        System.out.println(result);
    }

    /**
     * 将已经找出的不同数据的 map 根据 key 的层级结构封装成 josn 返回
     */
    private String convertMapToJson(Map<String, Object> map) {
        JSONObject resultJSONObject = new JSONObject();
        // 这个 for 循环用于拿到每个json变化的map
        for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> item = it.next();
            // key是路径
            String key = item.getKey();
            // value是变化的值
            Object value = item.getValue();
            String[] paths = key.split("\\.");
            int i = 0;
            Object remarkObject = null;//用于深度标识对象
            int indexAll = paths.length - 1;
            // while 循环是对每一个 map 进行处理
            while (i <= paths.length - 1) {
                String path = paths[i];
                if (i == 0) {
                    //初始化对象标识
                    if (resultJSONObject.containsKey(path)) {
                        remarkObject = resultJSONObject.get(path);
                    } else {
                        if (indexAll > i) {
                            if (paths[i + 1].matches("\\[[0-9]+\\]")) {
                                remarkObject = new JSONArray();
                            } else {
                                remarkObject = new JSONObject();
                            }
                            resultJSONObject.put(path, remarkObject);
                        } else {
                            resultJSONObject.put(path, value);
                        }
                    }
                    i++;
                    continue;
                }
                if (path.matches("\\[[0-9]+\\]")) {//匹配集合对象
                    int startIndex = path.lastIndexOf("[");
                    int endIndext = path.lastIndexOf("]");
                    int index = Integer.parseInt(path.substring(startIndex + 1, endIndext));
                    if (indexAll > i) {
                        if (paths[i + 1].matches("\\[[0-9]+\\]")) {
                            while (((JSONArray) remarkObject).size() <= index) {
                                if(((JSONArray) remarkObject).size() == index){
                                    ((JSONArray) remarkObject).add(index,new JSONArray());
                                }else{
                                    ((JSONArray) remarkObject).add(null);
                                }
                            }
                        } else {
                            // 这里还是在初始化remarkObject的格式，index表示数组中的第几个值；比如index=2，
                            // 数组中第三个值发生了变化，需要为 remarkObject 初始化三个值，前两个是 null，第三个是 JsonObject
                            while(((JSONArray) remarkObject).size() <= index){
                                if(((JSONArray) remarkObject).size() == index){
                                    ((JSONArray) remarkObject).add(index,new JSONObject());
                                }else{
                                    ((JSONArray) remarkObject).add(null);
                                }
                            }
                        }
                        remarkObject = ((JSONArray) remarkObject).get(index);
                    } else {
                        while(((JSONArray) remarkObject).size() <= index){
                            if(((JSONArray) remarkObject).size() == index){
                                ((JSONArray) remarkObject).add(index, value);
                            }else{
                                ((JSONArray) remarkObject).add(null);
                            }
                        }
                    }
                } else {
                    if (indexAll > i) {
                        if (paths[i + 1].matches("\\[[0-9]+\\]")) {
                            if(!((JSONObject) remarkObject).containsKey(path)){
                                ((JSONObject) remarkObject).put(path, new JSONArray());
                            }
                        } else {
                            if(!((JSONObject) remarkObject).containsKey(path)){
                                ((JSONObject) remarkObject).put(path, new JSONObject());
                            }
                        }
                        remarkObject = ((JSONObject) remarkObject).get(path);
                    } else {
                        ((JSONObject) remarkObject).put(path, value);
                    }
                }
                i++;
            }
        }
        return resultJSONObject.toString();
    }

    @Test
    public void testJSONObject() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("1", "2");
        jsonObject.put("put", jsonObject1);
        System.out.println("test");
    }


}






















