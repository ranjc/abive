package com.abive.util;

import org.apache.commons.lang.time.StopWatch;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranjiangchuan on 15/3/29.
 */
public class JsonUtil {
    private static final Logger LOG    = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Bean对象转换json字符串
     * @param value
     * @return
     */
    public final static String objectToJson(Object value) {
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = null;
        try {
            jsonGenerator = mapper.getJsonFactory().createJsonGenerator(writer);
            mapper.writeValue(jsonGenerator, value);
        } catch (IOException e) {
            LOG.error("对象转换成json失败", e);
        } finally {
            try {
                if (jsonGenerator != null) {
                    jsonGenerator.close();
                }
            } catch (IOException e) {
                LOG.error("", e);
            }
        }
        return writer.toString();
    }

    public static String objectToJson(Object pojo, boolean prettyPrint) {
        StringWriter sw = new StringWriter();
        JsonGenerator jg = null;
        try {
            jg = mapper.getJsonFactory().createJsonGenerator(sw);
            if (prettyPrint) {
                jg.useDefaultPrettyPrinter();
            }
            mapper.writeValue(jg, pojo);
        } catch (JsonGenerationException e) {
            LOG.error("对象转换成json失败", e);
        } catch (JsonMappingException e) {
            LOG.error("对象转换成json失败", e);
        } catch (IOException e) {
            LOG.error("对象转换成json失败", e);
        } finally {
            try {
                if (jg != null) {
                    jg.close();
                }
            } catch (IOException e) {
                LOG.error("", e);
            }
        }

        return sw.toString();
    }

    public final static ObjectMapper getMapperInstance() {
        return mapper;
    }

    /**
     * json字符串转换为对应Bean对象
     * @param json
     * @param clazz
     * @return
     */
    public static <V> V jsonToObject(String json, Class<V> clazz) {
        V value = null;
        try {
            if (clazz == String.class) {
                return clazz.cast(json);
            }
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            value = mapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("json转换成对象失败", e);
        }
        return value;
    }

    public static <T> List<T> jsonToList(String s, Class<T> clazz) {
        List<T> list = new ArrayList<T>();

        try {
            /*JSONArray jarr = new JSONArray(s);
            T value = null;
            for (int i = 0; i < jarr.length(); i++) {
                value = JSONUtils.jsonToObject(jarr.getString(i), clazz);
                list.add(value);
            }*/
        } catch (Exception e) {
            LOG.error("json转换成list失败", e);
        }
        return list;
    }

    /**
     * 将一组对象转换为JSON数组
     */
    public static String objectsToJsonArray(Object... objects) {
        String result = null;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("[");
            for (Object obj : objects) {
                obj = JsonUtil.objectToJson(obj);
                sb.append(obj);
                sb.append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");

            result = sb.toString();
        } catch (Exception e) {
            LOG.error("ObjectsToJsonArray 转换失败 {}", e);
        }
        return result;
    }

    public static void main(String[] args) {
        StopWatch clock = new StopWatch();
        clock.start();
        try {
            String json = "[\"aaa\",\"bbbb\"]";

            List<String> list = JsonUtil.jsonToList(json, String.class);
            System.out.println(list.size());
            System.out.println(list.get(0));

        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        clock.stop();
        System.out.println("cost:" + clock.getTime() + " ms");
    }
}
