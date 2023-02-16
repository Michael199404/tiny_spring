package com.chao.springframework.test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

public class JsonTest {

    @Test
    public void testSfJson() {
        // params
        String recordId = "39";
        String keyValueList = "[{\"id\":5528,\"keyCode\":\"NEED_TO_BE_INCLUDED_IN_MIPAY_BILL_CHANNELS\",\"keyName\":\"【平安账单】合并到钱包账单的渠道号（payType）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":6508,\"keyCode\":\"147\",\"keyName\":\"测试新增\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":368,\"keyCode\":\"XIAOMI_PARTNER_ID\",\"keyName\":\"捷付商户ID\",\"keyType\":1,\"keyValue\":\"1000000319\",\"enable\":1},{\"id\":363,\"keyCode\":\"MIPAY_PRIVATE_KEY\",\"keyName\":\"捷付商户私钥\",\"keyType\":1,\"keyValue\":\"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMdBG8ZRemJQcUwiNy++wflbOvj93UHLfPDJQSOn/UACTMo86zqlMS/dO/lgjUllDlGA7kpII8GfsswqifTWahah8z9S5k5a6geji5MhvMW3jLFfOGVSOV/xufVcibeInzyK4APEtoQFH4laKzrrz/HZy3+VLkodbbUHSiDO8LHlAgMBAAECgYEAlyjHn130kF0idnWGlEwM79TMGlTM47sx36FuL1SkBUk5ZDtu4KCtffhCJV+gD8wAXsiG/gdYSO2UQNsMqZd4BraKpFx9dSLEX+hWHMMe/6XUH2LNN5mxaSoDM0xPSPPBLccSYmxAYkVqscZL0+qxi5tlIyI4K2MVdSiakCyqB4kCQQD9SpRAf+Wbqy4eoiW0WmXXyOxkEdgvW/na9xL9Kk6B2/r0HXYnyV44dS89KAgfPvtwNaDmxbcv13OSU0NzA6QvAkEAyWKYb9M9wFw3Ye3cOnDeyIsyIs4ucM5Jfn3xMaq0DYnGVHMls7uCGinwaKIjTt7i9v6uVclrS2swGBEXAwBCKwJBAKse25HH1mOY/ouLmFWETRG/mJTyyU8VoHOiQJ3xGyD4cU7DKLQwgws5gDSc+v/BGTXxxUajhOM5d8b/oa3uY9kCQCTwTc+tKpV/FLWEwLGDhIm6Zn7V3NTaMcbe6P/YNOCK1fMlj9PQ4ANWj/9RRjKwVWdCvc2EfYa+6XpwoAzv3jMCQQD7MCB24LFCqf+Edc6mrnxSuFipwmL2Vy9p9hlCfjxjyKAV/AMLVHY5UEkSAXDXo7x+iaYEBB0U+uykrdD/JMyS\",\"enable\":1},{\"id\":364,\"keyCode\":\"MIPAY_PUBLIC_KEY\",\"keyName\":\"捷付支付公钥\",\"keyType\":1,\"keyValue\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDWwRHPVDCD81vulmut/+ME1+ZUMf9sf2S1jYmtTsOPP+cmYXFL7DCwWh59WOhVcHBsgcm6IYIJB8yCWP9T6mbeitKRLq8UiCctp+aCLNW6Q1/DdD0mzw0JDtZsAWZ8cfze7K/gV7OmnB9fA4iA4Wix6K4I68pwvafm3AyS12TSwIDAQAB\",\"enable\":1},{\"id\":359,\"keyCode\":\"CLIENT_TYPE\",\"keyName\":\"客户端类型\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":360,\"keyCode\":\"DEFAULT_PAY_INFO_ID\",\"keyName\":\"默认配置ID\",\"keyType\":1,\"keyValue\":\"000001\",\"enable\":1},{\"id\":365,\"keyCode\":\"PAY_INFO_ID\",\"keyName\":\"配置ID\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":358,\"keyCode\":\"configId\",\"keyName\":\"配置号(必填)\",\"keyType\":1,\"keyValue\":\"000001\",\"enable\":1},{\"id\":4447,\"keyCode\":\"SCENES\",\"keyName\":\"使用场景说明\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":5560,\"keyCode\":\"IS_NEED_RECONCILIATION\",\"keyName\":\"是否需要对账（需要填YES）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":5527,\"keyCode\":\"IS_NEED_PUSH_BILL_ON_SFTP\",\"keyName\":\"是否需要将渠道账单文件推送到sftp (yes)\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":362,\"keyCode\":\"IS_PAY_ITEM_RECOMMEND\",\"keyName\":\"收银台是否推荐（true,false）\",\"keyType\":1,\"keyValue\":\"false\",\"enable\":1},{\"id\":361,\"keyCode\":\"IS_PAY_ITEM_FOLD\",\"keyName\":\"收银台是否折叠（true-false）\",\"keyType\":1,\"keyValue\":\"false\",\"enable\":1},{\"id\":366,\"keyCode\":\"PAY_ITEM_PAY_TIP\",\"keyName\":\"收银台展示的营销文案\",\"keyType\":1,\"keyValue\":\"超0长文案1超2长3文4案5超6长7文8案9\",\"enable\":1},{\"id\":367,\"keyCode\":\"PAY_ITEM_PRIORITY\",\"keyName\":\"收银台展示的优先级（0-100）\",\"keyType\":1,\"keyValue\":\"2\",\"enable\":1}]";
        String enable = "1";
        String userId = "3150071171";

        // 将所有 params 转为 json 格式
        JSONObject paramsJson = new JSONObject();
        paramsJson.put("recordId", recordId);
        paramsJson.put("keyValueList", keyValueList);
        paramsJson.put("enable", enable);
        paramsJson.put("userId", userId);

        String paramsJsonString = paramsJson.toString();

        System.out.println(paramsJsonString);
    }

    @Test
    public void testSfJson2() {
        String paramsJsonString = "{\"recordId\":\"39\",\"keyValueList\":[{\"id\":5528,\"keyCode\":\"NEED_TO_BE_INCLUDED_IN_MIPAY_BILL_CHANNELS\",\"keyName\":\"【平安账单】合并到钱包账单的渠道号（payType）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":6508,\"keyCode\":\"147\",\"keyName\":\"测试新增\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":368,\"keyCode\":\"XIAOMI_PARTNER_ID\",\"keyName\":\"捷付商户ID\",\"keyType\":1,\"keyValue\":\"1000000319\",\"enable\":1},{\"id\":363,\"keyCode\":\"MIPAY_PRIVATE_KEY\",\"keyName\":\"捷付商户私钥\",\"keyType\":1,\"keyValue\":\"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMdBG8ZRemJQcUwiNy++wflbOvj93UHLfPDJQSOn/UACTMo86zqlMS/dO/lgjUllDlGA7kpII8GfsswqifTWahah8z9S5k5a6geji5MhvMW3jLFfOGVSOV/xufVcibeInzyK4APEtoQFH4laKzrrz/HZy3+VLkodbbUHSiDO8LHlAgMBAAECgYEAlyjHn130kF0idnWGlEwM79TMGlTM47sx36FuL1SkBUk5ZDtu4KCtffhCJV+gD8wAXsiG/gdYSO2UQNsMqZd4BraKpFx9dSLEX+hWHMMe/6XUH2LNN5mxaSoDM0xPSPPBLccSYmxAYkVqscZL0+qxi5tlIyI4K2MVdSiakCyqB4kCQQD9SpRAf+Wbqy4eoiW0WmXXyOxkEdgvW/na9xL9Kk6B2/r0HXYnyV44dS89KAgfPvtwNaDmxbcv13OSU0NzA6QvAkEAyWKYb9M9wFw3Ye3cOnDeyIsyIs4ucM5Jfn3xMaq0DYnGVHMls7uCGinwaKIjTt7i9v6uVclrS2swGBEXAwBCKwJBAKse25HH1mOY/ouLmFWETRG/mJTyyU8VoHOiQJ3xGyD4cU7DKLQwgws5gDSc+v/BGTXxxUajhOM5d8b/oa3uY9kCQCTwTc+tKpV/FLWEwLGDhIm6Zn7V3NTaMcbe6P/YNOCK1fMlj9PQ4ANWj/9RRjKwVWdCvc2EfYa+6XpwoAzv3jMCQQD7MCB24LFCqf+Edc6mrnxSuFipwmL2Vy9p9hlCfjxjyKAV/AMLVHY5UEkSAXDXo7x+iaYEBB0U+uykrdD/JMyS\",\"enable\":1},{\"id\":364,\"keyCode\":\"MIPAY_PUBLIC_KEY\",\"keyName\":\"捷付支付公钥\",\"keyType\":1,\"keyValue\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDWwRHPVDCD81vulmut/+ME1+ZUMf9sf2S1jYmtTsOPP+cmYXFL7DCwWh59WOhVcHBsgcm6IYIJB8yCWP9T6mbeitKRLq8UiCctp+aCLNW6Q1/DdD0mzw0JDtZsAWZ8cfze7K/gV7OmnB9fA4iA4Wix6K4I68pwvafm3AyS12TSwIDAQAB\",\"enable\":1},{\"id\":359,\"keyCode\":\"CLIENT_TYPE\",\"keyName\":\"客户端类型\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":360,\"keyCode\":\"DEFAULT_PAY_INFO_ID\",\"keyName\":\"默认配置ID\",\"keyType\":1,\"keyValue\":\"000001\",\"enable\":1},{\"id\":365,\"keyCode\":\"PAY_INFO_ID\",\"keyName\":\"配置ID\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":358,\"keyCode\":\"configId\",\"keyName\":\"配置号(必填)\",\"keyType\":1,\"keyValue\":\"000001\",\"enable\":1},{\"id\":4447,\"keyCode\":\"SCENES\",\"keyName\":\"使用场景说明\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":5560,\"keyCode\":\"IS_NEED_RECONCILIATION\",\"keyName\":\"是否需要对账（需要填YES）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":5527,\"keyCode\":\"IS_NEED_PUSH_BILL_ON_SFTP\",\"keyName\":\"是否需要将渠道账单文件推送到sftp (yes)\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":362,\"keyCode\":\"IS_PAY_ITEM_RECOMMEND\",\"keyName\":\"收银台是否推荐（true,false）\",\"keyType\":1,\"keyValue\":\"false\",\"enable\":1},{\"id\":361,\"keyCode\":\"IS_PAY_ITEM_FOLD\",\"keyName\":\"收银台是否折叠（true-false）\",\"keyType\":1,\"keyValue\":\"false\",\"enable\":1},{\"id\":366,\"keyCode\":\"PAY_ITEM_PAY_TIP\",\"keyName\":\"收银台展示的营销文案\",\"keyType\":1,\"keyValue\":\"超0长文案1超2长3文4案5超6长7文8案9\",\"enable\":1},{\"id\":367,\"keyCode\":\"PAY_ITEM_PRIORITY\",\"keyName\":\"收银台展示的优先级（0-100）\",\"keyType\":1,\"keyValue\":\"2\",\"enable\":1}],\"enable\":\"1\",\"userId\":\"3150071171\"}";
    }

    @Test
    public void testGson() {
        String paramsJsonString = "{\"recordId\":\"39\",\"keyValueList\":[{\"id\":5528,\"keyCode\":\"NEED_TO_BE_INCLUDED_IN_MIPAY_BILL_CHANNELS\",\"keyName\":\"【平安账单】合并到钱包账单的渠道号（payType）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":6508,\"keyCode\":\"147\",\"keyName\":\"测试新增\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":368,\"keyCode\":\"XIAOMI_PARTNER_ID\",\"keyName\":\"捷付商户ID\",\"keyType\":1,\"keyValue\":\"1000000319\",\"enable\":1},{\"id\":363,\"keyCode\":\"MIPAY_PRIVATE_KEY\",\"keyName\":\"捷付商户私钥\",\"keyType\":1,\"keyValue\":\"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMdBG8ZRemJQcUwiNy++wflbOvj93UHLfPDJQSOn/UACTMo86zqlMS/dO/lgjUllDlGA7kpII8GfsswqifTWahah8z9S5k5a6geji5MhvMW3jLFfOGVSOV/xufVcibeInzyK4APEtoQFH4laKzrrz/HZy3+VLkodbbUHSiDO8LHlAgMBAAECgYEAlyjHn130kF0idnWGlEwM79TMGlTM47sx36FuL1SkBUk5ZDtu4KCtffhCJV+gD8wAXsiG/gdYSO2UQNsMqZd4BraKpFx9dSLEX+hWHMMe/6XUH2LNN5mxaSoDM0xPSPPBLccSYmxAYkVqscZL0+qxi5tlIyI4K2MVdSiakCyqB4kCQQD9SpRAf+Wbqy4eoiW0WmXXyOxkEdgvW/na9xL9Kk6B2/r0HXYnyV44dS89KAgfPvtwNaDmxbcv13OSU0NzA6QvAkEAyWKYb9M9wFw3Ye3cOnDeyIsyIs4ucM5Jfn3xMaq0DYnGVHMls7uCGinwaKIjTt7i9v6uVclrS2swGBEXAwBCKwJBAKse25HH1mOY/ouLmFWETRG/mJTyyU8VoHOiQJ3xGyD4cU7DKLQwgws5gDSc+v/BGTXxxUajhOM5d8b/oa3uY9kCQCTwTc+tKpV/FLWEwLGDhIm6Zn7V3NTaMcbe6P/YNOCK1fMlj9PQ4ANWj/9RRjKwVWdCvc2EfYa+6XpwoAzv3jMCQQD7MCB24LFCqf+Edc6mrnxSuFipwmL2Vy9p9hlCfjxjyKAV/AMLVHY5UEkSAXDXo7x+iaYEBB0U+uykrdD/JMyS\",\"enable\":1},{\"id\":364,\"keyCode\":\"MIPAY_PUBLIC_KEY\",\"keyName\":\"捷付支付公钥\",\"keyType\":1,\"keyValue\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDWwRHPVDCD81vulmut/+ME1+ZUMf9sf2S1jYmtTsOPP+cmYXFL7DCwWh59WOhVcHBsgcm6IYIJB8yCWP9T6mbeitKRLq8UiCctp+aCLNW6Q1/DdD0mzw0JDtZsAWZ8cfze7K/gV7OmnB9fA4iA4Wix6K4I68pwvafm3AyS12TSwIDAQAB\",\"enable\":1},{\"id\":359,\"keyCode\":\"CLIENT_TYPE\",\"keyName\":\"客户端类型\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":360,\"keyCode\":\"DEFAULT_PAY_INFO_ID\",\"keyName\":\"默认配置ID\",\"keyType\":1,\"keyValue\":\"000001\",\"enable\":1},{\"id\":365,\"keyCode\":\"PAY_INFO_ID\",\"keyName\":\"配置ID\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":358,\"keyCode\":\"configId\",\"keyName\":\"配置号(必填)\",\"keyType\":1,\"keyValue\":\"000001\",\"enable\":1},{\"id\":4447,\"keyCode\":\"SCENES\",\"keyName\":\"使用场景说明\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":5560,\"keyCode\":\"IS_NEED_RECONCILIATION\",\"keyName\":\"是否需要对账（需要填YES）\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":5527,\"keyCode\":\"IS_NEED_PUSH_BILL_ON_SFTP\",\"keyName\":\"是否需要将渠道账单文件推送到sftp (yes)\",\"keyType\":1,\"keyValue\":\"\",\"enable\":1},{\"id\":362,\"keyCode\":\"IS_PAY_ITEM_RECOMMEND\",\"keyName\":\"收银台是否推荐（true,false）\",\"keyType\":1,\"keyValue\":\"false\",\"enable\":1},{\"id\":361,\"keyCode\":\"IS_PAY_ITEM_FOLD\",\"keyName\":\"收银台是否折叠（true-false）\",\"keyType\":1,\"keyValue\":\"false\",\"enable\":1},{\"id\":366,\"keyCode\":\"PAY_ITEM_PAY_TIP\",\"keyName\":\"收银台展示的营销文案\",\"keyType\":1,\"keyValue\":\"超0长文案1超2长3文4案5超6长7文8案9\",\"enable\":1},{\"id\":367,\"keyCode\":\"PAY_ITEM_PRIORITY\",\"keyName\":\"收银台展示的优先级（0-100）\",\"keyType\":1,\"keyValue\":\"2\",\"enable\":1}],\"enable\":\"1\",\"userId\":\"3150071171\"}";

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(paramsJsonString, JsonObject.class);

        String recordId = jsonObject.get("recordId").toString();
        String keyValueList = jsonObject.get("keyValueList").toString();

        System.out.println(jsonObject);
    }

//    private String convertMapToJson(Map<String, Object> map) {
//        JSONObject resultJSONObject = new JSONObject();
//        for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext(); ) {
//            Map.Entry<String, Object> item = it.next();
//            String key = item.getKey();
//            Object value = item.getValue();
//            String[] paths = key.split("\\.");
//            int i = 0;
//            Object remarkObject = null;//用於深度標識對象
//            int indexAll = paths.length - 1;
//            while (i <= paths.length - 1) {
//                String path = paths[i];
//                if (i == 0) {
//                    //初始化对象标识
//                    if (resultJSONObject.containsKey(path)) {
//                        remarkObject = resultJSONObject.get(path);
//                    } else {
//                        if (indexAll > i) {
//                            if (paths[i + 1].matches("\\[[0-9]+\\]")) {
//                                remarkObject = new JSONArray();
//                            } else {
//                                remarkObject = new JSONObject();
//                            }
//                            resultJSONObject.put(path, remarkObject);
//                        } else {
//                            resultJSONObject.put(path, value);
//                        }
//                    }
//                    i++;
//                    continue;
//                }
//                /**
//                 * ------------------------------------------------匹配集合对象-----------------------------------------------------
//                 */
//                // 如果 path 是 [0-9] 说明是一个数组
//                if (path.matches("\\[[0-9]+\\]")) {//匹配集合对象
//                    int startIndex = path.lastIndexOf("[");
//                    int endIndext = path.lastIndexOf("]");
//                    int index = Integer.parseInt(path.substring(startIndex + 1, endIndext));
//                    if (indexAll > i) {
//                        if (paths[i + 1].matches("\\[[0-9]+\\]")) {
//                            while (((JSONArray) remarkObject).size() <= index) {
//                                if(((JSONArray) remarkObject).size() == index){
//                                    ((JSONArray) remarkObject).add(index,new JSONArray());//有不同
//                                }else{
//                                    ((JSONArray) remarkObject).add(null);
//                                }
//                            }
//                        } else {
//                            while(((JSONArray) remarkObject).size() <= index){
//                                if(((JSONArray) remarkObject).size() == index){
//                                    ((JSONArray) remarkObject).add(index,new JSONObject());
//                                }else{
//                                    ((JSONArray) remarkObject).add(null);
//                                }
//                            }
//                        }
//                        remarkObject = ((JSONArray) remarkObject).get(index);
//                    } else {
//                        while(((JSONArray) remarkObject).size() <= index){
//                            if(((JSONArray) remarkObject).size() == index){
//                                ((JSONArray) remarkObject).add(index, value);
//                            }else{
//                                ((JSONArray) remarkObject).add(null);
//                            }
//                        }
//                    }
//                } else {
//                    if (indexAll > i) {
//                        if (paths[i + 1].matches("\\[[0-9]+\\]")) {
//                            if(!((JSONObject) remarkObject).containsKey(path)){
//                                ((JSONObject) remarkObject).put(path, new JSONArray());
//                            }
//                        } else {
//                            if(!((JSONObject) remarkObject).containsKey(path)){
//                                ((JSONObject) remarkObject).put(path, new JSONObject());
//                            }
//                        }
//                        remarkObject = ((JSONObject) remarkObject).get(path);
//                    } else {
//                        ((JSONObject) remarkObject).put(path, value);
//                    }
//                }
//                i++;
//            }
//        }
//        return JSON.toJSONString(resultJSONObject);
//    }







}
