package com.feather.common.json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.feather.common.utils.NumberUtils;

/**
 * 通用消息对象，基于Map实现的可嵌套数据结构。 支持JSON数据结构。
 * 
 * @author feather
 */
public class JSONObject extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(JSONObject.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    public JSONObject() {
        super();
    }

    public JSONObject(final JSONObject other) {
        super(other);
    }

    // ============================================================
    // marshal
    // ============================================================
    public static void marshal(File file, Object value) throws Exception {
        objectWriter.writeValue(file, value);
    }

    public static void marshal(OutputStream os, Object value) throws Exception {
        objectWriter.writeValue(os, value);
    }

    public static String marshal(Object value) throws Exception {
        return objectWriter.writeValueAsString(value);
    }

    public static byte[] marshalBytes(Object value) throws Exception {
        return objectWriter.writeValueAsBytes(value);
    }

    public static String toJsonString(Object value) {
        try {
            return objectWriter.writeValueAsString(value);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    // ============================================================
    // unmarshal
    // ============================================================
    public static JSONObject unmarshal(String str) throws Exception {
        return unmarshal(str, JSONObject.class);
    }

    public static <T> T unmarshal(File file, Class<T> valueType) throws Exception {
        try {
            return objectMapper.readValue(file, valueType);
        } catch (JsonParseException e) {
            throw e;
        } catch (JsonMappingException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    public static <T> T unmarshal(InputStream is, Class<T> valueType) throws Exception {
        try {
            return objectMapper.readValue(is, valueType);
        } catch (JsonParseException e) {
            throw e;
        } catch (JsonMappingException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    public static <T> T unmarshal(String str, Class<T> valueType) throws Exception {
        try {
            return objectMapper.readValue(str, valueType);
        } catch (JsonParseException e) {
            throw e;
        } catch (JsonMappingException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    public static <T> T unmarshal(byte[] bytes, Class<T> valueType) throws Exception {
        try {
            if (bytes == null) {
                bytes = new byte[0];
            }
            return objectMapper.readValue(bytes, 0, bytes.length, valueType);
        } catch (JsonParseException e) {
            throw e;
        } catch (JsonMappingException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    public static JSONObject parse(String str) {
        return parse(str, JSONObject.class);
    }

    public static <T> T parse(File file, Class<T> valueType) {
        try {
            return objectMapper.readValue(file, valueType);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T parse(InputStream is, Class<T> valueType) {
        try {
            return objectMapper.readValue(is, valueType);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T parse(String str, Class<T> valueType) {
        try {
            return objectMapper.readValue(str, valueType);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T parse(byte[] bytes, Class<T> valueType) {
        try {
            if (bytes == null) {
                bytes = new byte[0];
            }
            return objectMapper.readValue(bytes, 0, bytes.length, valueType);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static JSONObject toJSONObject(final Map<String, Object> map) {
        final JSONObject obj = new JSONObject();
        for (final Map.Entry<String, Object> ent : map.entrySet()) {
            obj.put(ent.getKey(), transfer(ent.getValue()));
        }
        return obj;
    }

    public static JSONArray toJSONArray(final Collection<Object> list) {
        final JSONArray arr = new JSONArray(list.size());
        for (final Object element : list) {
            arr.add(element);
        }
        return arr;
    }

    // ============================================================
    // to String
    // ============================================================
    @Override
    public String toString() {
        try {
            return marshal(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换为紧凑格式的字符串。
     * 
     * @return 返回本对象紧凑格式字符串。
     */
    public String toCompactString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ============================================================
    // convert
    // ============================================================
    public JSONObject getJSONObject(final String name) {
        return (JSONObject) get(name);
    }

    public JSONArray getJSONArray(final String name) {
        return (JSONArray) get(name);
    }

    public <T> T asBean(Class<T> beanClass) {
        try {
            return unmarshal(marshal(this), beanClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 重载基类的方法。如果 value 是 Map 类型，但不是 MessageObject 类型，则创建一个包含内容等同于原 Map 的
     * MessageObject 作为 value（注意：此后再更改 Map 的内容，将不会反映到 MessageObject 中）。
     * 重载此方法的目的是为了使JSON能够正确地解析为MessageObject对象。不建议直接调用此方法，请使用 set(name,
     * value)方法设置字段值。
     */
    @Override
    public Object put(String key, Object value) {
        return super.put(key, transfer(value));
    }

    // ============================================================
    // Integer
    // ============================================================
    public int getInt(final String name) {
        return getInteger(name, 0);
    }

    public int getInt(final String name, final int defaultValue) {
        return getInteger(name, defaultValue);
    }

    public Integer getInteger(final String name) {
        return getInteger(name, null);
    }

    public Integer getInteger(final String name, Integer defaultValue) {
        Integer val = NumberUtils.asInteger(get(name));
        return val != null ? val : defaultValue;
    }

    // ============================================================
    // Long
    // ============================================================
    public long getLong(final String name) {
        return getLongObject(name, 0L);
    }

    public long getLong(final String name, final long defaultValue) {
        return getLongObject(name, defaultValue);
    }

    public Long getLongObject(final String name) {
        return getLongObject(name, null);
    }

    public Long getLongObject(final String name, final Long defaultValue) {
        Long val = NumberUtils.asLong(get(name));
        return val != null ? val : defaultValue;
    }

    // ============================================================
    // Float
    // ============================================================
    public float getFloat(final String name) {
        return getFloatObject(name, 0F);
    }

    public float getFloat(final String name, final float defaultValue) {
        return getFloatObject(name, defaultValue);
    }

    public Float getFloatObject(final String name) {
        return getFloatObject(name, null);
    }

    public Float getFloatObject(final String name, final Float defaultValue) {
        Float val = NumberUtils.asFloat(get(name));
        return val != null ? val : defaultValue;
    }

    // ============================================================
    // Double
    // ============================================================
    public double getDouble(final String name) {
        return getDoubleObject(name, 0D);
    }

    public double getDouble(final String name, final double defaultValue) {
        return getDoubleObject(name, defaultValue);
    }

    public Double getDoubleObject(final String name) {
        return getDoubleObject(name, null);
    }

    public Double getDoubleObject(final String name, final Double defaultValue) {
        Double val = NumberUtils.asDouble(get(name));
        return val != null ? val : defaultValue;
    }

    // ============================================================
    // Boolean
    // ============================================================
    public boolean getBool(final String name) {
        return getBoolean(name, false);
    }

    public boolean getBool(final String name, final boolean defaultValue) {
        return getBoolean(name, defaultValue);
    }

    public Boolean getBoolean(final String name) {
        return getBoolean(name, false);
    }

    public Boolean getBoolean(final String name, final Boolean defaultValue) {
        Object value = get(name);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue() != 0.0;
        } else if (value instanceof String) {
            return Boolean.valueOf((String) value);
        }
        return defaultValue;
    }

    // ============================================================
    // String
    // ============================================================
    public String getString(final String name) {
        return getString(name, null);
    }

    public String getString(final String name, final String defaultValue) {
        Object value = get(name);
        if (value instanceof String) {
            return (String) value;
        } else if (value != null) {
            return value.toString();
        }
        return defaultValue;
    }

    // ============================================================
    // private
    // ============================================================
    /**
     * 将所有层次中凡是Map类型同时又不是MessageObject的类型，转换为MessageObject类型。
     */
    @SuppressWarnings("unchecked")
    private static Object transfer(final Object value) {
        if (!(value instanceof JSONObject) && value instanceof Map) {
            return toJSONObject((Map<String, Object>) value);
        } else if (!(value instanceof JSONArray) && value instanceof Collection) {
            return toJSONArray((Collection<Object>) value);
        } else {
            return value;
        }
    }

    /**
     * 数组结构。
     */
    public static class JSONArray extends ArrayList<Object> {
        private static final long serialVersionUID = 1L;

        public JSONArray() {
            super();
        }

        public JSONArray(int size) {
            super(size);
        }

        @Override
        public String toString() {
            try {
                return JSONObject.marshal(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Object set(int index, Object element) {
            return super.set(index, transfer(element));
        }

        @Override
        public boolean add(Object element) {
            return super.add(transfer(element));
        }

        @Override
        public void add(int index, Object element) {
            super.add(index, transfer(element));
        }

        public JSONObject getJSONObject(int index) {
            return (JSONObject) get(index);
        }

        public Double getJSONdouble(int index) {
            return (Double) get(index);
        }
    }
}
