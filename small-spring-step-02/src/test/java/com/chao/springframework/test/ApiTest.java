package com.chao.springframework.test;

import com.chao.springframework.beans.factory.config.BeanDefinition;
import com.chao.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.chao.springframework.test.bean.UserService;
import com.google.common.base.Joiner;
import com.google.gson.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 总结：
 * 1. 在Spring Bean容器的实现类中要重点关注类之间的职责和关系
 */
public class ApiTest {

    String original = "{\"product\":\"MIPAY\",\"feeInfo\":[{\"minAmount\":100,\"feeModel\":\"BY_TRADE_RATE\"}]}";
    String modificy = "{\"product\":\"MIPAY\",\"feeInfo\":[{\"minAmount\":101,\"feeModel\":\"BY_TRADE_RATE\"}]}";
    //        String original = "{\"product\":\"MIPAY\",\"feeInfo\":[{\"minAmount\":100,\"feeModel\":\"BY_TRADE_RATE\"},{\"minAmount\":100,\"feeModel\":\"BY_TRADE_RATE\"}]}";
//        String modificy = "{\"product\":\"MIPAY1\",\"feeInfo\":[{\"minAmount\":100,\"feeModel\":\"BY_TRADE_RATE\"},{\"minAmount\":100,\"feeModel\":\"BY_TRADE_RATE1\"}]}";


    @Test
    public void testBeanFactory() {
        //1. 初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //2. 注册bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        //3. 第一次获取bean
        UserService userService = (UserService)beanFactory.getBean("userService");
        userService.queryUserInfo();
        //4. 第二次获取bean from Singleton
        UserService userService_singleton = (UserService) beanFactory.getBean("userService");
        userService_singleton.queryUserInfo();
    }

    /**
     * Gson 用法测试
     */
    @Test
    public void testJsonObjectMethod() {
        // 将 json 格式的字符串 original 转化为 JsonObject
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(original, JsonObject.class);
        // 检查 jsonObject 中是否有指定名称成员
        boolean res = jsonObject.has("product");
        // 返回具有指定名称的成员
//        JsonElement product = jsonObject.getAsJsonObject("product");
        String product = jsonObject.get("product").getAsString();
        System.out.println(product);
    }

    @Test
    public void testConvertJsonToMap() {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(original, JsonObject.class);
        Map<String, Object> oldMap = new LinkedHashMap<>();
//        convertJsonToMap(jsonObject, "", oldMap);
        System.out.println("test");
    }

    @Test
    public void testCompareTwoJson() {
//        String ori = "{\"businessLicenseNo\":\"91110108596084056A\",\"businessLicenseAddress\":\"北京市海淀区西二旗中路33号院6号楼8层018号\",\"businessExpiration\":\"2040-03-01\",\"establishDate\":\"2010-04-01\",\"registeredFund\":148800000000,\"currency\":0,\"currencyDesc\":\"人民币\",\"businessLicensePic\":\"http://staging.merchant.cash.payment.pt.xiaomi.com/download/merchantProfile?fileId=3f0b092d9e9445bf8c76472af090a03a&userId=3150071171\",\"policiesPic\":\"http://staging.merchant.cash.payment.pt.xiaomi.com/download/merchantProfile?fileId=e4e8f45ce9c14b80823809155c6c85dc\",\"executives\":[{\"personId\":3013,\"name\":\"数据丢失123\",\"position\":\"CEOO\",\"idType\":6,\"idNo\":\"652901196611026716\",\"idStart\":\"\",\"idExpire\":\"\",\"address\":\"heaven\"},{\"personId\":3014,\"name\":\"数据丢失456\",\"position\":\"CFO\",\"idType\":6,\"idNo\":\"\",\"idStart\":\"\",\"idExpire\":\"\",\"address\":\"\"}],\"beneficiaries\":[{\"name\":\"王川传\",\"idType\":0,\"idNo\":\"652901196611026716\",\"idStart\":\"2022-09-01\",\"idExpire\":\"2022-09-30\",\"address\":\"345345\",\"percent\":\"29\",\"updateTime\":\"2022-11-18 16:18:56\"},{\"name\":\"王小川2\",\"idType\":2,\"idNo\":\"652901196611026716\",\"idStart\":\"\",\"idExpire\":\"9999-12-31\",\"address\":\"北京海淀\",\"percent\":\"31\",\"updateTime\":\"2022-11-18 16:18:56\"}],\"merchantType\":0,\"companyName\":\"北京小米移动软件有限公司\",\"businessScope\":\"营业执照经营访问test_repeat\",\"mainMcc\":7372,\"mccDesc\":\"计算机软件开发、系统集成、数据处理服务\",\"representativeName\":\"王川川2\",\"representativeIdType\":0,\"representativeIdTypeDesc\":\"身份证\",\"representativeIdNo\":\"652901196611026716\",\"representativeIdStartDate\":\"2022-09-02\",\"enabled\":1,\"reviewStatus\":1,\"reviewNote\":\"ok\",\"recordId\":\"193\",\"dominateBizId\":\"1000000319\",\"cooperateType\":\"SPECIAL\"}";
//        String mod = "{\"businessLicenseNo\":\"91110108596084056A\",\"businessLicenseAddress\":\"北京市海淀区西二旗中路33号院6号楼8层019号\",\"businessExpiration\":\"2040-03-01\",\"establishDate\":\"2010-04-01\",\"registeredFund\":148800000000,\"currency\":0,\"currencyDesc\":\"人民币\",\"businessLicensePic\":\"http://staging.merchant.cash.payment.pt.xiaomi.com/download/merchantProfile?fileId=3f0b092d9e9445bf8c76472af090a03a&userId=3150071171\",\"policiesPic\":\"http://staging.merchant.cash.payment.pt.xiaomi.com/download/merchantProfile?fileId=e4e8f45ce9c14b80823809155c6c85dc\",\"executives\":[{\"personId\":3013,\"name\":\"数据丢失123\",\"position\":\"CEOO\",\"idType\":6,\"idNo\":\"652901196611026716\",\"idStart\":\"\",\"idExpire\":\"\",\"address\":\"heaven\"},{\"personId\":3014,\"name\":\"数据丢失457\",\"position\":\"CEO\",\"idType\":6,\"idNo\":\"\",\"idStart\":\"\",\"idExpire\":\"\",\"address\":\"\"}],\"beneficiaries\":[{\"name\":\"王川传1\",\"idType\":0,\"idNo\":\"652901196611026716\",\"idStart\":\"2022-09-01\",\"idExpire\":\"2022-09-30\",\"address\":\"345345\",\"percent\":\"29\",\"updateTime\":\"2022-11-18 16:18:56\"},{\"name\":\"王小川2\",\"idType\":2,\"idNo\":\"652901196611026716\",\"idStart\":\"\",\"idExpire\":\"9999-12-31\",\"address\":\"北京海淀1\",\"percent\":\"31\",\"updateTime\":\"2022-11-18 16:18:56\"}],\"merchantType\":0,\"companyName\":\"北京小米移动软件有限公司\",\"businessScope\":\"营业执照经营访问test_repeat\",\"mainMcc\":7372,\"mccDesc\":\"计算机软件开发、系统集成、数据处理服务\",\"representativeName\":\"王川川2\",\"representativeIdType\":0,\"representativeIdTypeDesc\":\"身份证\",\"representativeIdNo\":\"652901196611026716\",\"representativeIdStartDate\":\"2022-09-02\",\"enabled\":1,\"reviewStatus\":1,\"reviewNote\":\"ok\",\"recordId\":\"193\",\"dominateBizId\":\"1000000319\",\"cooperateType\":\"SPECIAL\"}";
        String ori = "{\"merchantId\":\"1000013579\",\"chargeType\":\"NORMAL_PAY\",\"payType\":\"WEIXINPAY\",\"payInfoId\":\"000003\",\"clientType\":\"APK\",\"version\":10,\"payChannel\":\"Chinaums51003\",\"whiteList\":[{\"miId\":3150028131,\"operator\":\"hewanying\"},{\"miId\":3150000497,\"operator\":\"hewanying\"},{\"miId\":3150071171,\"operator\":\"zhangchaochao1\"}]}";
        String mod = "{\"merchantId\":\"1000013579\",\"chargeType\":\"NORMAL_PAY\",\"payType\":\"WEIXINPAY\",\"payInfoId\":\"000003\",\"clientType\":\"APK\",\"version\":10,\"payChannel\":\"Chinaums51003\",\"whiteList\":[{\"miId\":3150028131,\"operator\":\"hewanying\"},{\"miId\":0,\"operator\":\"\"},{\"miId\":3150071171,\"operator\":\"zhangchaochao1\"}]}";
        String compareTwoJson = compareTwoJson(ori, mod);
        System.out.println(compareTwoJson);
    }

    /**
     * 遍历 json
     * 思路：如果是集合，必须是有序的且按照规则排好序的
     */
    public String compareTwoJson(String original, String modification) {
        Gson gson = new Gson();
        JsonObject originalJson = gson.fromJson(original, JsonObject.class);
        JsonObject modificationJson = gson.fromJson(modification, JsonObject.class);
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
        if (json instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject) json;
            Iterator iterator = jsonObject.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                Object value = jsonObject.get(key);
                String newRoot = "".equals(root) ? key + "" : root + "." + key;
                if (value instanceof JsonObject || value instanceof JsonArray) {
                    convertJsonToMap(value, newRoot, resultMap);
                } else {
                    resultMap.put(newRoot, value);
                }
            }
        } else if (json instanceof JsonArray) {
            JsonArray jsonArray = (JsonArray) json;
            for (int i = 0; i < jsonArray.size(); i++) {
                Object value = jsonArray.get(i);
                String newRoot = "".equals(root) ? "[" + i + "]" : root + ".[" + i + "]";
                if (value instanceof JsonObject || value instanceof JsonArray) {
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

    /**
     * 使用 Gson 改写 map 封装为 json
     */
    public String convertMapToJson(Map<String, Object> map) {
        JsonObject resultJsonObject = new JsonObject();
        for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> item = it.next();
            String key = item.getKey();
            Object value = item.getValue();
            String[] paths = key.split("\\.");
            int i = 0;
            Object remarkObject = null;
            int indexAll = paths.length - 1;
            while (i <= paths.length - 1) {
                String path = paths[i];
                /**
                 * -------------------------------初始化对象标识---------------------------------------------------
                 */
                if (i == 0) {
                    if (resultJsonObject.has(path)) {
                        remarkObject = resultJsonObject.get(path);
                    } else {
                        if (indexAll > i) {
                            if (paths[i + 1].matches("\\[[0-9]+\\]")) {
                                remarkObject = new JsonArray();
                            } else {
                                remarkObject = new JsonObject();
                            }
                            resultJsonObject.add(path, (JsonElement) remarkObject);
                        } else {
                            resultJsonObject.add(path, new Gson().toJsonTree(value).getAsJsonObject());
                        }
                    }
                    i++;
                    continue;
                }
                /**
                 * -----------------------------------------匹配集合对象------------------------------------------------
                 */
                // 如果 path 是 [0-9] 说明是一个数组
                if (path.matches("\\[[0-9]+\\]")) {//匹配集合对象
                    int startIndex = path.lastIndexOf("[");
                    int endIndex = path.lastIndexOf("]");
                    int index = Integer.parseInt(path.substring(startIndex + 1, endIndex));
                    if (indexAll > i) {
                        if (paths[i + 1].matches("\\[[0-9]+\\]")) {
                            while (((JsonArray)remarkObject).size() <= index) {
                                if (((JsonArray)remarkObject).size() == index) {
                                    ((JsonArray)remarkObject).add(new JsonArray());//有不同
                                } else {
                                    ((JsonArray) remarkObject).add((JsonElement) null);
                                }
                            }
                        } else {
                            while (((JsonArray)remarkObject).size() <= index) {
                                if (((JsonArray)remarkObject).size() == index) {
                                    ((JsonArray)remarkObject).add(new JsonObject());//有不同
                                } else {
                                    ((JsonArray)remarkObject).add((JsonElement) null);
                                }
                            }
                        }
                        remarkObject = ((JsonArray)remarkObject).get(index);
                        // indexAll == i 说明
                    } else {
                        while (((JsonArray)remarkObject).size() <= index) {
                            if (((JsonArray)remarkObject).size() == index) {
                                ((JsonArray)remarkObject).add(new JsonObject());//有不同
                            } else {
                                ((JsonArray)remarkObject).add((JsonElement) null);
                            }
                        }
                    }
                    // 如果 path 不是 [0-9]，那么说明是一个对象
                } else {
                    if (indexAll > i) {
                        if (paths[i + 1].matches("\\[[0-9]+\\]")) {
                            if (!((JsonObject)remarkObject).has(path)) {
                                ((JsonObject)remarkObject).add(path, new JsonArray());
                            }
                        } else {
                            if (!((JsonObject)remarkObject).has(path)) {
                                ((JsonObject)remarkObject).add(path, new JsonObject());
                            }
                        }
                        remarkObject = ((JsonObject) remarkObject).get(path);
                    } else {
                        ((JsonObject)remarkObject).add(path, new Gson().toJsonTree(value).getAsJsonObject());
                    }
                }
                i++;
            }
        }
        String result = resultJsonObject.toString();
        return result;
    }

    @Test
    public void genParamsJsonString() {
        String recordId = "39";
        String keyValueList = "[{\"id\":5528,\"keyCode\":\"NEED_TO_BE_INCLUDED_IN_MIPAY_BILL_CHANNELS\",\"keyName\":\"【平安账单】合并到钱包账单的渠道号（payType）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":6508,\"keyCode\":\"147\",\"keyName\":\"测试新增\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":368,\"keyCode\":\"XIAOMI_PARTNER_ID\",\"keyName\":\"捷付商户ID\",\"keyType\":1,\"keyValue\":\"1000000319\",\"enable\":1},{\"id\":363,\"keyCode\":\"MIPAY_PRIVATE_KEY\",\"keyName\":\"捷付商户私钥\",\"keyType\":1,\"keyValue\":\"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMdBG8ZRemJQcUwiNy++wflbOvj93UHLfPDJQSOn/UACTMo86zqlMS/dO/lgjUllDlGA7kpII8GfsswqifTWahah8z9S5k5a6geji5MhvMW3jLFfOGVSOV/xufVcibeInzyK4APEtoQFH4laKzrrz/HZy3+VLkodbbUHSiDO8LHlAgMBAAECgYEAlyjHn130kF0idnWGlEwM79TMGlTM47sx36FuL1SkBUk5ZDtu4KCtffhCJV+gD8wAXsiG/gdYSO2UQNsMqZd4BraKpFx9dSLEX+hWHMMe/6XUH2LNN5mxaSoDM0xPSPPBLccSYmxAYkVqscZL0+qxi5tlIyI4K2MVdSiakCyqB4kCQQD9SpRAf+Wbqy4eoiW0WmXXyOxkEdgvW/na9xL9Kk6B2/r0HXYnyV44dS89KAgfPvtwNaDmxbcv13OSU0NzA6QvAkEAyWKYb9M9wFw3Ye3cOnDeyIsyIs4ucM5Jfn3xMaq0DYnGVHMls7uCGinwaKIjTt7i9v6uVclrS2swGBEXAwBCKwJBAKse25HH1mOY/ouLmFWETRG/mJTyyU8VoHOiQJ3xGyD4cU7DKLQwgws5gDSc+v/BGTXxxUajhOM5d8b/oa3uY9kCQCTwTc+tKpV/FLWEwLGDhIm6Zn7V3NTaMcbe6P/YNOCK1fMlj9PQ4ANWj/9RRjKwVWdCvc2EfYa+6XpwoAzv3jMCQQD7MCB24LFCqf+Edc6mrnxSuFipwmL2Vy9p9hlCfjxjyKAV/AMLVHY5UEkSAXDXo7x+iaYEBB0U+uykrdD/JMyS\",\"enable\":1},{\"id\":364,\"keyCode\":\"MIPAY_PUBLIC_KEY\",\"keyName\":\"捷付支付公钥\",\"keyType\":1,\"keyValue\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDWwRHPVDCD81vulmut/+ME1+ZUMf9sf2S1jYmtTsOPP+cmYXFL7DCwWh59WOhVcHBsgcm6IYIJB8yCWP9T6mbeitKRLq8UiCctp+aCLNW6Q1/DdD0mzw0JDtZsAWZ8cfze7K/gV7OmnB9fA4iA4Wix6K4I68pwvafm3AyS12TSwIDAQAB\",\"enable\":1},{\"id\":359,\"keyCode\":\"CLIENT_TYPE\",\"keyName\":\"客户端类型\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":360,\"keyCode\":\"DEFAULT_PAY_INFO_ID\",\"keyName\":\"默认配置ID\",\"keyType\":1,\"keyValue\":\"000001\",\"enable\":1},{\"id\":365,\"keyCode\":\"PAY_INFO_ID\",\"keyName\":\"配置ID\",\"keyType\":1,\"keyValue\":\"133\",\"enable\":1},{\"id\":358,\"keyCode\":\"configId\",\"keyName\":\"配置号(必填)\",\"keyType\":1,\"keyValue\":\"000001\",\"enable\":1},{\"id\":4447,\"keyCode\":\"SCENES\",\"keyName\":\"使用场景说明\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":5560,\"keyCode\":\"IS_NEED_RECONCILIATION\",\"keyName\":\"是否需要对账（需要填YES）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":5527,\"keyCode\":\"IS_NEED_PUSH_BILL_ON_SFTP\",\"keyName\":\"是否需要将渠道账单文件推送到sftp+(yes)\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":362,\"keyCode\":\"IS_PAY_ITEM_RECOMMEND\",\"keyName\":\"收银台是否推荐（true,false）\",\"keyType\":1,\"keyValue\":\"false\",\"enable\":1},{\"id\":361,\"keyCode\":\"IS_PAY_ITEM_FOLD\",\"keyName\":\"收银台是否折叠（true-false）\",\"keyType\":1,\"keyValue\":\"false\",\"enable\":1},{\"id\":366,\"keyCode\":\"PAY_ITEM_PAY_TIP\",\"keyName\":\"收银台展示的营销文案\",\"keyType\":1,\"keyValue\":\"超0长文案1超2长3文4案5超6长7文8案9\",\"enable\":1},{\"id\":367,\"keyCode\":\"PAY_ITEM_PRIORITY\",\"keyName\":\"收银台展示的优先级（0-100）\",\"keyType\":1,\"keyValue\":\"2\",\"enable\":1}]";
        String enable = "1";
        String userId = "3150071171";

        JSONObject paramsJson = new JSONObject();
        paramsJson.put("recordId", recordId);
        paramsJson.put("keyValueList", keyValueList);
        paramsJson.put("enable", enable);
        paramsJson.put("userId", userId);

        String paramsJsonString = paramsJson.toString();

        System.out.println(paramsJsonString);
    }

    @Test
    public void resolverParamsJsonString() {
        String paramsJsonString = "{\"recordId\":\"39\",\"keyValueList\":[{\"id\":5528,\"keyCode\":\"NEED_TO_BE_INCLUDED_IN_MIPAY_BILL_CHANNELS\",\"keyName\":\"【平安账单】合并到钱包账单的渠道号（payType）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":6508,\"keyCode\":\"147\",\"keyName\":\"测试新增\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":368,\"keyCode\":\"XIAOMI_PARTNER_ID\",\"keyName\":\"捷付商户ID\",\"keyType\":1,\"keyValue\":\"1000000319\",\"enable\":1},{\"id\":363,\"keyCode\":\"MIPAY_PRIVATE_KEY\",\"keyName\":\"捷付商户私钥\",\"keyType\":1,\"keyValue\":\"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMdBG8ZRemJQcUwiNy++wflbOvj93UHLfPDJQSOn/UACTMo86zqlMS/dO/lgjUllDlGA7kpII8GfsswqifTWahah8z9S5k5a6geji5MhvMW3jLFfOGVSOV/xufVcibeInzyK4APEtoQFH4laKzrrz/HZy3+VLkodbbUHSiDO8LHlAgMBAAECgYEAlyjHn130kF0idnWGlEwM79TMGlTM47sx36FuL1SkBUk5ZDtu4KCtffhCJV+gD8wAXsiG/gdYSO2UQNsMqZd4BraKpFx9dSLEX+hWHMMe/6XUH2LNN5mxaSoDM0xPSPPBLccSYmxAYkVqscZL0+qxi5tlIyI4K2MVdSiakCyqB4kCQQD9SpRAf+Wbqy4eoiW0WmXXyOxkEdgvW/na9xL9Kk6B2/r0HXYnyV44dS89KAgfPvtwNaDmxbcv13OSU0NzA6QvAkEAyWKYb9M9wFw3Ye3cOnDeyIsyIs4ucM5Jfn3xMaq0DYnGVHMls7uCGinwaKIjTt7i9v6uVclrS2swGBEXAwBCKwJBAKse25HH1mOY/ouLmFWETRG/mJTyyU8VoHOiQJ3xGyD4cU7DKLQwgws5gDSc+v/BGTXxxUajhOM5d8b/oa3uY9kCQCTwTc+tKpV/FLWEwLGDhIm6Zn7V3NTaMcbe6P/YNOCK1fMlj9PQ4ANWj/9RRjKwVWdCvc2EfYa+6XpwoAzv3jMCQQD7MCB24LFCqf+Edc6mrnxSuFipwmL2Vy9p9hlCfjxjyKAV/AMLVHY5UEkSAXDXo7x+iaYEBB0U+uykrdD/JMyS\",\"enable\":1},{\"id\":364,\"keyCode\":\"MIPAY_PUBLIC_KEY\",\"keyName\":\"捷付支付公钥\",\"keyType\":1,\"keyValue\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDWwRHPVDCD81vulmut/+ME1+ZUMf9sf2S1jYmtTsOPP+cmYXFL7DCwWh59WOhVcHBsgcm6IYIJB8yCWP9T6mbeitKRLq8UiCctp+aCLNW6Q1/DdD0mzw0JDtZsAWZ8cfze7K/gV7OmnB9fA4iA4Wix6K4I68pwvafm3AyS12TSwIDAQAB\",\"enable\":1},{\"id\":359,\"keyCode\":\"CLIENT_TYPE\",\"keyName\":\"客户端类型\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":360,\"keyCode\":\"DEFAULT_PAY_INFO_ID\",\"keyName\":\"默认配置ID\",\"keyType\":1,\"keyValue\":\"000001\",\"enable\":1},{\"id\":365,\"keyCode\":\"PAY_INFO_ID\",\"keyName\":\"配置ID\",\"keyType\":1,\"keyValue\":\"133\",\"enable\":1},{\"id\":358,\"keyCode\":\"configId\",\"keyName\":\"配置号(必填)\",\"keyType\":1,\"keyValue\":\"000001\",\"enable\":1},{\"id\":4447,\"keyCode\":\"SCENES\",\"keyName\":\"使用场景说明\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":5560,\"keyCode\":\"IS_NEED_RECONCILIATION\",\"keyName\":\"是否需要对账（需要填YES）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":5527,\"keyCode\":\"IS_NEED_PUSH_BILL_ON_SFTP\",\"keyName\":\"是否需要将渠道账单文件推送到sftp+(yes)\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":362,\"keyCode\":\"IS_PAY_ITEM_RECOMMEND\",\"keyName\":\"收银台是否推荐（true,false）\",\"keyType\":1,\"keyValue\":\"false\",\"enable\":1},{\"id\":361,\"keyCode\":\"IS_PAY_ITEM_FOLD\",\"keyName\":\"收银台是否折叠（true-false）\",\"keyType\":1,\"keyValue\":\"false\",\"enable\":1},{\"id\":366,\"keyCode\":\"PAY_ITEM_PAY_TIP\",\"keyName\":\"收银台展示的营销文案\",\"keyType\":1,\"keyValue\":\"超0长文案1超2长3文4案5超6长7文8案9\",\"enable\":1},{\"id\":367,\"keyCode\":\"PAY_ITEM_PRIORITY\",\"keyName\":\"收银台展示的优先级（0-100）\",\"keyType\":1,\"keyValue\":\"2\",\"enable\":1}],\"enable\":\"1\",\"userId\":\"3150071171\"}";
        // 转为 JSONObject
        JSONObject jsonObject = JSONObject.fromObject(paramsJsonString);
        String recordId = (String) jsonObject.get("recordId");
        System.out.println(recordId);
    }

    @Test
    public void genPageRenderData() {
        JSONObject pageRenderJson = new JSONObject();
        pageRenderJson.put("reviewConfigName", "upay运营后台支付方式配置修改");
        pageRenderJson.put("reviewConfigKey", "upay_admin_channel_key_update");

        JSONArray tableData = new JSONArray();

        /**
         * ----------------------------------- form1 -----------------------------------------------
         */
        JSONObject form1 = new JSONObject();
        form1.put("caption", "运营后台商户主体信息");
        form1.put("key", "mainMerchantInfo");
        form1.put("enableComparision", true);
        JSONArray paramsArrayForm1 = new JSONArray();

        JSONObject params1From1 = new JSONObject();
        params1From1.put("head", "企业名称");
        params1From1.put("variable", "companyName");
        params1From1.put("type", "string");

        JSONObject params2Form1 = new JSONObject();
        params2Form1.put("head", "社会统一信用代码");
        params2Form1.put("variable", "businessLicenseNo");
        params2Form1.put("type", "string");

        JSONObject params3Form1 = new JSONObject();
        params3Form1.put("head", "控股股东或实际控制人");
        params3Form1.put("variable", "beneficiaries");
        params3Form1.put("type", "bean");

        paramsArrayForm1.add(params1From1);
        paramsArrayForm1.add(params2Form1);
        paramsArrayForm1.add(params3Form1);
        form1.put("render", paramsArrayForm1);

        /**
         * ------------------------------------- form2 -------------------------------------------
         */
        JSONObject form2 = new JSONObject();
        form2.put("caption", "控股股东或实际控制人");
        form2.put("key", "beneficiaries");
        form2.put("enableComparision", true);
        JSONArray paramsArrayForm2 = new JSONArray();

        JSONObject params1Form2 = new JSONObject();
        params1Form2.put("head", "控股股东或实际控制人名称");
        params1Form2.put("variable", "name");
        params1Form2.put("type", "string");

        JSONObject params2Form2 = new JSONObject();
        params2Form2.put("head", "证件类型");
        params2Form2.put("variable", "idType");
        params2Form2.put("type", "string");

        paramsArrayForm2.add(params1Form2);
        paramsArrayForm2.add(params2Form2);
        form2.put("render", paramsArrayForm2);

        /**
         * -----------------------------------------------------------------------------------------
         */
        tableData.add(form1);
        tableData.add(form2);

        pageRenderJson.put("tableData", tableData);

        String pageRenderJsonString = pageRenderJson.toString();

        System.out.println(pageRenderJsonString);

    }

    @Test
    public void primaryKeyGroupToPrimaryParams() {
        String primaryKeyGroup = "id,recordId,data";
        String paramsJsonString = "{\"id\":19,\"recordId\":\"39\",\"data\":\"1000000319\",\"keyValueList\":[{\"id\":5528,\"keyCode\":\"NEED_TO_BE_INCLUDED_IN_MIPAY_BILL_CHANNELS\",\"keyName\":\"【平安账单】合并到钱包账单的渠道号（payType）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":6508,\"keyCode\":\"147\",\"keyName\":\"测试新增\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":368,\"keyCode\":\"XIAOMI_PARTNER_ID\",\"keyName\":\"捷付商户ID\",\"keyType\":1,\"keyValue\":\"1000000319\",\"enable\":1},{\"id\":363,\"keyCode\":\"MIPAY_PRIVATE_KEY\",\"keyName\":\"捷付商户私钥\",\"keyType\":1,\"keyValue\":\"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMdBG8ZRemJQcUwiNy++wflbOvj93UHLfPDJQSOn/UACTMo86zqlMS/dO/lgjUllDlGA7kpII8GfsswqifTWahah8z9S5k5a6geji5MhvMW3jLFfOGVSOV/xufVcibeInzyK4APEtoQFH4laKzrrz/HZy3+VLkodbbUHSiDO8LHlAgMBAAECgYEAlyjHn130kF0idnWGlEwM79TMGlTM47sx36FuL1SkBUk5ZDtu4KCtffhCJV+gD8wAXsiG/gdYSO2UQNsMqZd4BraKpFx9dSLEX+hWHMMe/6XUH2LNN5mxaSoDM0xPSPPBLccSYmxAYkVqscZL0+qxi5tlIyI4K2MVdSiakCyqB4kCQQD9SpRAf+Wbqy4eoiW0WmXXyOxkEdgvW/na9xL9Kk6B2/r0HXYnyV44dS89KAgfPvtwNaDmxbcv13OSU0NzA6QvAkEAyWKYb9M9wFw3Ye3cOnDeyIsyIs4ucM5Jfn3xMaq0DYnGVHMls7uCGinwaKIjTt7i9v6uVclrS2swGBEXAwBCKwJBAKse25HH1mOY/ouLmFWETRG/mJTyyU8VoHOiQJ3xGyD4cU7DKLQwgws5gDSc+v/BGTXxxUajhOM5d8b/oa3uY9kCQCTwTc+tKpV/FLWEwLGDhIm6Zn7V3NTaMcbe6P/YNOCK1fMlj9PQ4ANWj/9RRjKwVWdCvc2EfYa+6XpwoAzv3jMCQQD7MCB24LFCqf+Edc6mrnxSuFipwmL2Vy9p9hlCfjxjyKAV/AMLVHY5UEkSAXDXo7x+iaYEBB0U+uykrdD/JMyS\",\"enable\":1},{\"id\":364,\"keyCode\":\"MIPAY_PUBLIC_KEY\",\"keyName\":\"捷付支付公钥\",\"keyType\":1,\"keyValue\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDWwRHPVDCD81vulmut/+ME1+ZUMf9sf2S1jYmtTsOPP+cmYXFL7DCwWh59WOhVcHBsgcm6IYIJB8yCWP9T6mbeitKRLq8UiCctp+aCLNW6Q1/DdD0mzw0JDtZsAWZ8cfze7K/gV7OmnB9fA4iA4Wix6K4I68pwvafm3AyS12TSwIDAQAB\",\"enable\":1},{\"id\":359,\"keyCode\":\"CLIENT_TYPE\",\"keyName\":\"客户端类型\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":360,\"keyCode\":\"DEFAULT_PAY_INFO_ID\",\"keyName\":\"默认配置ID\",\"keyType\":1,\"keyValue\":\"000001\",\"enable\":1},{\"id\":365,\"keyCode\":\"PAY_INFO_ID\",\"keyName\":\"配置ID\",\"keyType\":1,\"keyValue\":\"133\",\"enable\":1},{\"id\":358,\"keyCode\":\"configId\",\"keyName\":\"配置号(必填)\",\"keyType\":1,\"keyValue\":\"000001\",\"enable\":1},{\"id\":4447,\"keyCode\":\"SCENES\",\"keyName\":\"使用场景说明\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":5560,\"keyCode\":\"IS_NEED_RECONCILIATION\",\"keyName\":\"是否需要对账（需要填YES）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":5527,\"keyCode\":\"IS_NEED_PUSH_BILL_ON_SFTP\",\"keyName\":\"是否需要将渠道账单文件推送到sftp+(yes)\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":362,\"keyCode\":\"IS_PAY_ITEM_RECOMMEND\",\"keyName\":\"收银台是否推荐（true,false）\",\"keyType\":1,\"keyValue\":\"false\",\"enable\":1},{\"id\":361,\"keyCode\":\"IS_PAY_ITEM_FOLD\",\"keyName\":\"收银台是否折叠（true-false）\",\"keyType\":1,\"keyValue\":\"false\",\"enable\":1},{\"id\":366,\"keyCode\":\"PAY_ITEM_PAY_TIP\",\"keyName\":\"收银台展示的营销文案\",\"keyType\":1,\"keyValue\":\"超0长文案1超2长3文4案5超6长7文8案9\",\"enable\":1},{\"id\":367,\"keyCode\":\"PAY_ITEM_PRIORITY\",\"keyName\":\"收银台展示的优先级（0-100）\",\"keyType\":1,\"keyValue\":\"2\",\"enable\":1}],\"enable\":\"1\",\"userId\":\"3150071171\"}";
        String s = primaryKeyGroupToPrimaryParams(primaryKeyGroup, paramsJsonString);
    }

    private String primaryKeyGroupToPrimaryParams(String primaryKeyGroup, String paramsJsonString) {
        // 解析 primaryKeyGroup, 各个参数名之间通过 "+" 连接
        if (StringUtils.isNotBlank(primaryKeyGroup)) {
            String[] paramNames = primaryKeyGroup.split(",");
            JSONObject paramsJsonObject = JSONObject.fromObject(paramsJsonString);
            List<String> paramsList = new ArrayList();
            for (int i = 0; i < paramNames.length; i++) {
                if (paramsJsonObject.containsKey(paramNames[i])) {
                    paramsList.add(paramsJsonObject.getString(paramNames[i]));
                }
            }
            String join = Joiner.on(",").join(paramsList);
            System.out.println(join);
        }
        return null;
    }

    @Test
    public void traverseJSONObject() {
        String jsonString = "{\"147\":\"test_contrast\",\"XIAOMI_PARTNER_ID\":\"6000001182\",\"DEFAULT_PAY_INFO_ID\":\"1000000\",\"PAY_INFO_ID\":\"\",\"configId\":\"1000000\",\"SCENES\":\"test_contrast_first_test\",\"IS_NEED_PUSH_BILL_ON_SFTP\":\"yes\",\"IS_PAY_ITEM_RECOMMEND\":\"yes\",\"IS_PAY_ITEM_FOLD\":\"\",\"PAY_ITEM_PAY_TIP\":\"wenanwenan\",\"PAY_ITEM_PRIORITY\":\"0\"}";
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String value = (String) jsonObject.get(key);
            System.out.println("json");
        }
    }

    @Test
    public void testGenJson() {
        String ori = "{\"businessLicenseNo\":\"91110108596084056A\",\"businessLicenseAddress\":\"北京市海淀区西二旗中路33号院6号楼8层018号\",\"businessExpiration\":\"2040-03-01\",\"establishDate\":\"2010-04-01\",\"registeredFund\":148800000000,\"currency\":0,\"currencyDesc\":\"人民币\",\"businessLicensePic\":\"http://staging.merchant.cash.payment.pt.xiaomi.com/download/merchantProfile?fileId=3f0b092d9e9445bf8c76472af090a03a&userId=3150071171\",\"policiesPic\":\"http://staging.merchant.cash.payment.pt.xiaomi.com/download/merchantProfile?fileId=e4e8f45ce9c14b80823809155c6c85dc\",\"executives\":[{\"personId\":3013,\"name\":\"数据丢失123\",\"position\":\"CEOO\",\"idType\":6,\"idNo\":\"652901196611026716\",\"idStart\":\"\",\"idExpire\":\"\",\"address\":\"heaven\"},{\"personId\":3014,\"name\":\"数据丢失456\",\"position\":\"CFO\",\"idType\":6,\"idNo\":\"\",\"idStart\":\"\",\"idExpire\":\"\",\"address\":\"\"}],\"beneficiaries\":[{\"name\":\"王川传\",\"idType\":0,\"idNo\":\"652901196611026716\",\"idStart\":\"2022-09-01\",\"idExpire\":\"2022-09-30\",\"address\":\"345345\",\"percent\":\"29\",\"updateTime\":\"2022-11-18 16:18:56\"},{\"name\":\"王小川2\",\"idType\":2,\"idNo\":\"652901196611026716\",\"idStart\":\"\",\"idExpire\":\"9999-12-31\",\"address\":\"北京海淀\",\"percent\":\"31\",\"updateTime\":\"2022-11-18 16:18:56\"}],\"merchantType\":0,\"companyName\":\"北京小米移动软件有限公司\",\"businessScope\":\"营业执照经营访问test_repeat\",\"mainMcc\":7372,\"mccDesc\":\"计算机软件开发、系统集成、数据处理服务\",\"representativeName\":\"王川川2\",\"representativeIdType\":0,\"representativeIdTypeDesc\":\"身份证\",\"representativeIdNo\":\"652901196611026716\",\"representativeIdStartDate\":\"2022-09-02\",\"enabled\":1,\"reviewStatus\":1,\"reviewNote\":\"ok\",\"recordId\":\"193\",\"dominateBizId\":\"1000000319\",\"cooperateType\":\"SPECIAL\"}";
        JSONObject oriJSON = JSONObject.fromObject(ori);

        String diffFields = "{\"businessLicenseAddress\":{\"newValue\":\"北京市海淀区西二旗中路33号院6号楼8层019号\",\"oldValue\":\"北京市海淀区西二旗中路33号院6号楼8层018号\"},\"executives\":[null,{\"name\":{\"newValue\":\"数据丢失457\",\"oldValue\":\"数据丢失456\"},\"position\":{\"newValue\":\"CEO\",\"oldValue\":\"CFO\"}}],\"beneficiaries\":[{\"name\":{\"newValue\":\"王川传1\",\"oldValue\":\"王川传\"}},{\"address\":{\"newValue\":\"北京海淀1\",\"oldValue\":\"北京海淀\"}}]}";
        JSONObject diffJSON = JSONObject.fromObject(diffFields);

        JSONObject data = new JSONObject();
        data.put("original", oriJSON);
        data.put("diffFields", diffJSON);

        JSONObject res = new JSONObject();
        res.put("code", "200");
        res.put("data", data);

        System.out.println(res);

    }

}
