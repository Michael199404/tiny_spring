package com.chao.springframework.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chao.springframework.beans.PropertyValue;
import com.chao.springframework.beans.PropertyValues;
import com.chao.springframework.beans.factory.config.BeanDefinition;
import com.chao.springframework.beans.factory.config.BeanReference;
import com.chao.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.chao.springframework.test.bean.UserDao;
import com.chao.springframework.test.bean.UserService;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApiTest {

    @Test
    public void test_BeanFactory() {
        // 1. 初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. UserDao 注册
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 3. UserService 设置属性【uId、userDao】
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        // 4. UserService 注入 Bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 5. UserService 获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
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
        JSONObject originalJson = JSONObject.parseObject(original);
        JSONObject modificationJson = JSONObject.parseObject(modification);
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
        String original = "{\"product\":\"MIPAY\",\"feeInfo\":[{\"minAmount\":100,\"feeModel\":\"BY_TRADE_RATE\"},{\"minAmount\":100,\"feeModel\":\"BY_TRADE_RATE\"}]}";
        String modificy = "{\"product\":\"MIPAY\",\"feeInfo\":[{\"minAmount\":100,\"feeModel\":\"BY_TRADE_RATE\"},{\"minAmount\":100,\"feeModel\":\"BY_TRADE_RATE1\"}]}";
        String result = compareTwoJson(original, modificy);
        System.out.println(result);
    }

    @Test
    public void testCompareTwoJson() {
        String ori = "{\"userId\":\"3150071171\",\"recordId\":\"370\",\"keyValueList\":[{\"id\":7452,\"keyCode\":\"147\",\"keyName\":\"测试新增\",\"keyType\":1,\"keyValue\":\"test_contrast\",\"enable\":1},{\"id\":7453,\"keyCode\":\"XIAOMI_PARTNER_ID\",\"keyName\":\"捷付商户ID\",\"keyType\":1,\"keyValue\":\"6000001182\",\"enable\":1},{\"id\":7454,\"keyCode\":\"MIPAY_PRIVATE_KEY\",\"keyName\":\"捷付商户私钥\",\"keyType\":1,\"keyValue\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNlmfRY7b64PImFWWxtoeLH8wRM1fTUwRBc1lgu8ixcpjelYdWDq0OWjpFSJgH1XwwADF5b2O7cb4IpmDTuIUUFcwUbuOWJQm5aRfM9PWVB9R/mO/qzbOYh+1TbcLDNnxoRV6rhp/kDt2JAvlSg+O43P1Gg58IniCum8qIuUtUNwIDAQAB\",\"enable\":1},{\"id\":7455,\"keyCode\":\"MIPAY_PUBLIC_KEY\",\"keyName\":\"捷付支付公钥\",\"keyType\":1,\"keyValue\":\"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM2WZ9Fjtvrg8iYVZbG2h4sfzBEz V9NTBEFzWWC7yLFymN6Vh1YOrQ5aOkVImAfVfDAAMXlvY7txvgimYNO4hRQVzBRu45YlCblpF8z0 9ZUH1H+Y7+rNs5iH7VNtwsM2fGhFXquGn+QO3YkC+VKD47jc/UaDnwieIK6byoi5S1Q3AgMBAAEC gYAzCdnwQ78TfXDgP4OloNj5kwVIZ+Sv0/ZEgWx49WWXmAmXiKu2k6Im5AN/W8xSdY+FIyclLKAn 0s8IurVjGnX+2pPTwHBTEsLwo5W3hoVVOlfD7Leiz1f/1HTY3yUO075+FJfmdsS2fqN+BJzG9Ygw KS4x4GTcNTkYCUnpdrhOYQJBAOokEGScefv5R89Es8K0udz9so/ZAYlks74NzNkO/QmFQpS1zeuq IOh4QJOlzZYpXLFLAcAh2DYhKr6b04TxMhkCQQDgx+uNmLd5X3C2JB0QML960UomBDHRn19yiT+W RtxJJA5izkP+qqhRMZGxZgedRnb18RhyMW8PREFFB+UPEKLPAkBvNLfQgX1yjwBrUk8qwBmy+5AO wFv5fKneL8HGqlbGadOou/zU3JYKs+q/9bUCiBytXFm1fp2pf/FEXNCGyOQpAkEAl43TwO2VzwzN dtEUk2T6xXk1EDrFyDs3ZIcrS0xNnDbmSDIgYCV6RPwx6jULrzviW2KKA/xWkCmKBFwVUbn6uwJB AIy0aZYxCXV2ukadCTWk5UNBpar+iNQNZw/8HJhZySdBJS9bYiFJo64ztd3Bghv30kr0oNOqu0AN E29NYMnXv94=\",\"enable\":1},{\"id\":7456,\"keyCode\":\"DEFAULT_PAY_INFO_ID\",\"keyName\":\"默认配置ID\",\"keyType\":1,\"keyValue\":\"1000000\",\"enable\":1},{\"id\":7457,\"keyCode\":\"PAY_INFO_ID\",\"keyName\":\"配置ID\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":7458,\"keyCode\":\"configId\",\"keyName\":\"配置号(必填)\",\"keyType\":1,\"keyValue\":\"1000000\",\"enable\":1},{\"id\":7459,\"keyCode\":\"SCENES\",\"keyName\":\"使用场景说明\",\"keyType\":1,\"keyValue\":\"test_contrast_first_test\",\"enable\":1},{\"id\":7460,\"keyCode\":\"IS_NEED_PUSH_BILL_ON_SFTP\",\"keyName\":\"是否需要将渠道账单文件推送到sftp (yes)\",\"keyType\":1,\"keyValue\":\"yes\",\"enable\":1},{\"id\":7461,\"keyCode\":\"IS_PAY_ITEM_RECOMMEND\",\"keyName\":\"收银台是否推荐（true,false）\",\"keyType\":1,\"keyValue\":\"yes\",\"enable\":1},{\"id\":7462,\"keyCode\":\"IS_PAY_ITEM_FOLD\",\"keyName\":\"收银台是否折叠（true-false）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":7463,\"keyCode\":\"PAY_ITEM_PAY_TIP\",\"keyName\":\"收银台展示的营销文案\",\"keyType\":1,\"keyValue\":\"wenanwenan\",\"enable\":1},{\"id\":7464,\"keyCode\":\"PAY_ITEM_PRIORITY\",\"keyName\":\"收银台展示的优先级（0-100）\",\"keyType\":1,\"keyValue\":\"0\",\"enable\":1}],\"enable\":\"1\",\"reviewUri\":\"/channelKey/update\",\"versionMessage\":\"first modify to initialize history record\",\"identification\":\"370\",\"reviewRecordId\":\"2\"}";
        String mod = "{\"enable\":\"1\",\"userId\":\"3150071171\",\"recordId\":\"370\",\"keyValueList\":[{\"id\":7465,\"keyCode\":\"147\",\"keyName\":\"测试新增\",\"keyType\":1,\"keyValue\":\"test_contrast\",\"enable\":1},{\"id\":7466,\"keyCode\":\"XIAOMI_PARTNER_ID\",\"keyName\":\"捷付商户ID\",\"keyType\":1,\"keyValue\":\"6000001182\",\"enable\":1},{\"id\":7467,\"keyCode\":\"MIPAY_PRIVATE_KEY\",\"keyName\":\"捷付商户私钥\",\"keyType\":1,\"keyValue\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNlmfRY7b64PImFWWxtoeLH8wRM1fTUwRBc1lgu8ixcpjelYdWDq0OWjpFSJgH1XwwADF5b2O7cb4IpmDTuIUUFcwUbuOWJQm5aRfM9PWVB9R/mO/qzbOYh+1TbcLDNnxoRV6rhp/kDt2JAvlSg+O43P1Gg58IniCum8qIuUtUNwIDAQAB\",\"enable\":1},{\"id\":7468,\"keyCode\":\"MIPAY_PUBLIC_KEY\",\"keyName\":\"捷付支付公钥\",\"keyType\":1,\"keyValue\":\"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM2WZ9Fjtvrg8iYVZbG2h4sfzBEz V9NTBEFzWWC7yLFymN6Vh1YOrQ5aOkVImAfVfDAAMXlvY7txvgimYNO4hRQVzBRu45YlCblpF8z0 9ZUH1H+Y7+rNs5iH7VNtwsM2fGhFXquGn+QO3YkC+VKD47jc/UaDnwieIK6byoi5S1Q3AgMBAAEC gYAzCdnwQ78TfXDgP4OloNj5kwVIZ+Sv0/ZEgWx49WWXmAmXiKu2k6Im5AN/W8xSdY+FIyclLKAn 0s8IurVjGnX+2pPTwHBTEsLwo5W3hoVVOlfD7Leiz1f/1HTY3yUO075+FJfmdsS2fqN+BJzG9Ygw KS4x4GTcNTkYCUnpdrhOYQJBAOokEGScefv5R89Es8K0udz9so/ZAYlks74NzNkO/QmFQpS1zeuq IOh4QJOlzZYpXLFLAcAh2DYhKr6b04TxMhkCQQDgx+uNmLd5X3C2JB0QML960UomBDHRn19yiT+W RtxJJA5izkP+qqhRMZGxZgedRnb18RhyMW8PREFFB+UPEKLPAkBvNLfQgX1yjwBrUk8qwBmy+5AO wFv5fKneL8HGqlbGadOou/zU3JYKs+q/9bUCiBytXFm1fp2pf/FEXNCGyOQpAkEAl43TwO2VzwzN dtEUk2T6xXk1EDrFyDs3ZIcrS0xNnDbmSDIgYCV6RPwx6jULrzviW2KKA/xWkCmKBFwVUbn6uwJB AIy0aZYxCXV2ukadCTWk5UNBpar+iNQNZw/8HJhZySdBJS9bYiFJo64ztd3Bghv30kr0oNOqu0AN E29NYMnXv94=\",\"enable\":1},{\"id\":7469,\"keyCode\":\"DEFAULT_PAY_INFO_ID\",\"keyName\":\"默认配置ID\",\"keyType\":1,\"keyValue\":\"1000001\",\"enable\":1},{\"id\":7470,\"keyCode\":\"PAY_INFO_ID\",\"keyName\":\"配置ID\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":7471,\"keyCode\":\"configId\",\"keyName\":\"配置号(必填)\",\"keyType\":1,\"keyValue\":\"1000001\",\"enable\":1},{\"id\":7472,\"keyCode\":\"SCENES\",\"keyName\":\"使用场景说明\",\"keyType\":1,\"keyValue\":\"test_contrast_first_test1\",\"enable\":1},{\"id\":7473,\"keyCode\":\"IS_NEED_PUSH_BILL_ON_SFTP\",\"keyName\":\"是否需要将渠道账单文件推送到sftp (yes)\",\"keyType\":1,\"keyValue\":\"yes\",\"enable\":1},{\"id\":7474,\"keyCode\":\"IS_PAY_ITEM_RECOMMEND\",\"keyName\":\"收银台是否推荐（true,false）\",\"keyType\":1,\"keyValue\":\"yes\",\"enable\":1},{\"id\":7475,\"keyCode\":\"IS_PAY_ITEM_FOLD\",\"keyName\":\"收银台是否折叠（true-false）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":7476,\"keyCode\":\"PAY_ITEM_PAY_TIP\",\"keyName\":\"收银台展示的营销文案\",\"keyType\":1,\"keyValue\":\"wenanwenan\",\"enable\":1},{\"id\":7477,\"keyCode\":\"PAY_ITEM_PRIORITY\",\"keyName\":\"收银台展示的优先级（0-100）\",\"keyType\":1,\"keyValue\":\"0\",\"enable\":1}]}";
        String compareTwoJson = compareTwoJson(ori, mod);
        System.out.println(compareTwoJson);
    }

    /**
     * 将已经找出的不同数据的 map 根据 key 的层级结构封装成 josn 返回
     */
    private String convertMapToJson(Map<String, Object> map) {
        JSONObject resultJSONObject = new JSONObject();
        for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> item = it.next();
            String key = item.getKey();
            Object value = item.getValue();
            String[] paths = key.split("\\.");
            int i = 0;
            Object remarkObject = null;//用於深度標識對象
            int indexAll = paths.length - 1;
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


}
