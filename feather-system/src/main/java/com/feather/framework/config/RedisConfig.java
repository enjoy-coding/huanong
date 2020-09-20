package com.feather.framework.config;

import java.io.Serializable;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feather.common.config.MessageDelegate;
import com.feather.common.config.MessageReceiveDelegate;
import com.feather.common.config.TopicMessageDelegate;
import com.feather.common.json.JSONObject;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置加载
 * 
 * @author feather
 */
@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private Integer timeout;

    @Value("${spring.redis.maxIdle}")
    private Integer maxIdle;

    @Value("${spring.redis.maxActive}")
    private String maxActive;

    @Value("${spring.redis.maxTotal}")
    private Integer maxTotal;

    @Value("${spring.redis.maxWaitMillis}")
    private Long maxWaitMillis;

    @Value("${spring.redis.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;

    @Value("${spring.redis.numTestsPerEvictionRun}")
    private Integer numTestsPerEvictionRun;

    @Value("${spring.redis.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;

    @Value("${spring.redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.redis.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.redis.db0.expire}")
    private Integer db0Expire;

    @Value("${spring.redis.db1.expire}")
    private Integer db1Expire;

    @Autowired(required = false)
    private SimpMessageSendingOperations messageSendingOperations;

    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

    public static final String DEFAULT_TOPIC_CHANNEL_PATTERN = "/topic/channel/message/delegate";
    public static final String DEFAULT_LISTENER_METHOD = "onReceiveMessage";

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲数
        jedisPoolConfig.setMaxIdle(maxIdle);
        // 连接池的最大数据库连接数
        jedisPoolConfig.setMaxTotal(maxTotal);
        // 最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        // 在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        return jedisPoolConfig;
    }

    @Primary
    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig config) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        // 设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);
        // 常规缓存规定放在Database 1
        redisStandaloneConfiguration.setDatabase(1);
        // 获得默认的连接池构造
        // redisConnectionFactory对于Standalone模式的没有（RedisStandaloneConfiguration，JedisPoolConfig）的构造函数
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcf = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration
                .builder();
        jpcf.poolConfig(config);
        // 通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcf.build();
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Primary
    @Bean
    @SuppressWarnings("rawtypes")
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    /*
     * @Bean(name = "redisStringTemplate") public RedisTemplate<String, String>
     * redisStringTemplate(RedisConnectionFactory factory) {
     * RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
     * StringRedisSerializer stringSerializer = new StringRedisSerializer();
     * redisTemplate.setKeySerializer(stringSerializer);
     * redisTemplate.setHashKeySerializer(stringSerializer);
     * redisTemplate.setValueSerializer(stringSerializer);
     * redisTemplate.setHashValueSerializer(stringSerializer);
     * redisTemplate.setConnectionFactory(factory); return redisTemplate; }
     */

    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }

    @Primary
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(db1Expire))
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(createRedisSerializer()))
                .disableCachingNullValues();
        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
    }

    private RedisSerializer<Object> createRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(om);
        return serializer;
    }

    // ====================================================================================
    // Lock
    // ====================================================================================
    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, "spring-boot");
    }

    // ====================================================================================
    // Message
    // ====================================================================================
    /**
     * RedisMessageListenerContainer
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        return container;
    }

    /**
     * MessageListenerAdapter
     */
    @Bean
    public TopicMessageDelegate redisTopicMessageDelegate(StringRedisTemplate template) {
        TopicMessageDelegateImpl topicDelegate = new TopicMessageDelegateImpl(template, messageSendingOperations);
        return topicDelegate;
    }

    /**
     * MessageListenerAdapter
     */
    @Bean
    public static MessageListenerAdapter redisMessageListenerAdapter(RedisMessageListenerContainer container,
            TopicMessageDelegate topicMessageDelegate) {
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(topicMessageDelegate,
                DEFAULT_LISTENER_METHOD);
        container.addMessageListener(listenerAdapter, new PatternTopic(DEFAULT_TOPIC_CHANNEL_PATTERN));
        return listenerAdapter;
    }

    // ====================================================================================
    // Cache
    // ====================================================================================
    /**
     * crazycake RedisManager（之后的Bean都是For shiro）
     * 
     * @return
     */
    @Bean
    public org.crazycake.shiro.RedisManager redisManager() {
        org.crazycake.shiro.RedisManager redisManager = new org.crazycake.shiro.RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        if (StringUtils.isNotEmpty(password)) {
            redisManager.setPassword(password);
        }
        // 默认2000毫秒(Protocol.DEFAULT_TIMEOUT)
        if (timeout != null) {
            redisManager.setTimeout(timeout);
        }
        redisManager.setDatabase(0);
        return redisManager;
    }

    @Bean
    public org.crazycake.shiro.RedisCacheManager redisCacheManager() {
        org.crazycake.shiro.RedisCacheManager redisCacheManager = new org.crazycake.shiro.RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        // 选择属性字段作为缓存标识，这里选择account字段
        redisCacheManager.setPrincipalIdFieldName("userName");
        // 设置信息缓存时间（秒）, 默认1800
        if (db0Expire != null) {
            redisCacheManager.setExpire(db0Expire);
        }
        return redisCacheManager;
    }

    // ====================================================================================
    // Message Class
    // ====================================================================================
    public static class TopicMessageDelegateImpl implements TopicMessageDelegate {
        private Map<String, MessageDelegate> delegateMap = new ConcurrentHashMap<>();
        private StringRedisTemplate template;
        private SimpMessageSendingOperations operations;

        public TopicMessageDelegateImpl(StringRedisTemplate template, SimpMessageSendingOperations operations) {
            this.template = template;
            this.operations = operations;
        }

        @Override
        public MessageDelegate getDelegate(String topic) {
            return getDelegate(topic, null);
        }

        @Override
        public MessageDelegate getDelegate(String topic, MessageReceiveDelegate receiver) {
            MessageDelegate delegate = null;
            if (StringUtils.isNotEmpty(topic)) {
                delegate = delegateMap.get(topic);
                if (delegate == null) {
                    delegate = new MessageDelegateImpl(template, topic, receiver, operations);
                    delegateMap.put(topic, delegate);
                }
            }
            return delegate;
        }

        public void onReceiveMessage(String message) {
            try {
                MessageEntry entry = JSONObject.unmarshal(message, MessageEntry.class);
                String topic = entry.getTopic();
                MessageDelegate delegate = delegateMap.get(topic);
                if (delegate != null) {
                    MessageReceiveDelegate receiver = delegate.getMessageReceiveDelegate();
                    if (receiver != null) {
                        receiver.onReceive(entry.getPayload());
                    }
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    public static class MessageDelegateImpl implements MessageDelegate, MessageReceiveDelegate {
        private StringRedisTemplate template;
        private String topic;
        private MessageReceiveDelegate receiver;
        private SimpMessageSendingOperations operations;

        public MessageDelegateImpl(StringRedisTemplate template, String topic, MessageReceiveDelegate receiver,
                SimpMessageSendingOperations operations) {
            this.template = template;
            this.topic = topic;
            this.receiver = receiver != null ? receiver : this;
            this.operations = operations;
        }

        @Override
        public String getTopic() {
            return topic;
        }

        @Override
        public MessageReceiveDelegate getMessageReceiveDelegate() {
            return receiver;
        }

        @Override
        public void send(Object payload) {
            MessageEntry entry = new MessageEntry(topic, payload);
            String json = JSONObject.toJsonString(entry);
            template.convertAndSend(DEFAULT_TOPIC_CHANNEL_PATTERN, json);
        }

        @Override
        public void onReceive(Object payload) {
            if (operations != null) {
                operations.convertAndSend(topic, payload);
            }
        }
    }

    public static class MessageEntry implements Serializable {
        private static final long serialVersionUID = 1L;

        public String topic;
        public Object payload;

        public MessageEntry() {

        }

        public MessageEntry(String topic, Object payload) {
            this.topic = topic;
            this.payload = payload;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public Object getPayload() {
            return payload;
        }

        public void setPayload(Object payload) {
            this.payload = payload;
        }
    }
}
