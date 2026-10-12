package com.mongo_db.connect.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class CustomBeans {
    @Bean
    public RedisTemplate<String,Object> getRedisTemplateBean(RedisConnectionFactory redisConnectionFactory){
         RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
         redisTemplate.setConnectionFactory(redisConnectionFactory);

         redisTemplate.setKeySerializer(new StringRedisSerializer());
         redisTemplate.setHashKeySerializer(new GenericJacksonJsonRedisSerializer(new ObjectMapper()));

         return redisTemplate;
    }
}
