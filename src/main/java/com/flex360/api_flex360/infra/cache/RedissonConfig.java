package com.flex360.api_flex360.infra.cache;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

  @Value("${spring.data.redis.host:localhost}")
  private String redisHost;

  @Value("${spring.data.redis.port:6379}")
  private int redisPort;

  @Value("${spring.data.redis.password:}")
  private String redisPassword;

  @Bean
  public RedissonClient redissonClient() {
    try {
      Config config = new Config();
      config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort);
      return Redisson.create(config);
  } catch (Exception e) {
     
      System.err.println("Erro ao conectar ao Redis: " + e.getMessage());
      return null;  
  }
  }
}