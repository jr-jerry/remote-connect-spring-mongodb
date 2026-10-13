package com.mongo_db.connect.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

@Configuration
public class CustomBeans {
    @Bean
    public RedisTemplate<String,Object> getRedisTemplateBean(RedisConnectionFactory redisConnectionFactory){

        PolymorphicTypeValidator validatorClasses= BasicPolymorphicTypeValidator
                .builder()
                .allowIfBaseType("com.mongo_db.connect")
                .build();

         GenericJacksonJsonRedisSerializer jsonRedisSerializer=
                 GenericJacksonJsonRedisSerializer
                 .builder()
                 .enableDefaultTyping(validatorClasses )//only serialize or deserialize "com.mongo_db.connect package type class"
                 .typePropertyName("@class")
                 .typeValidator(validatorClasses)// protect from malcious code
                 .build();

         RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
         redisTemplate.setConnectionFactory(redisConnectionFactory);

         redisTemplate.setKeySerializer(new StringRedisSerializer());
         redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//         redisTemplate.setHashKeySerializer( jsonRedisSerializer);
         redisTemplate.setHashValueSerializer(jsonRedisSerializer);

         return redisTemplate;
    }
}
