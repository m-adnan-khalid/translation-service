package com.app.translationservice.config;

import com.app.translationservice.dto.TranslationResponseDTO;
import java.util.List;
import java.util.Map;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

  public static final String TRANSLATIONS_CACHE = "translations";
  public static final String TRANSLATION_EXPORTS_CACHE = "translationExports";
  public static final String SEARCH_RESULTS_CACHE = "searchResults";

  @Bean
  public CacheManager ehCacheManager() {
    CachingProvider provider = Caching.getCachingProvider();
    CacheManager cacheManager = provider.getCacheManager();

    // Configuration for individual translations cache
    CacheConfiguration<Integer, TranslationResponseDTO> translationsConfig =
        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Integer.class,
                TranslationResponseDTO.class,
                ResourcePoolsBuilder.heap(1000)) // 1000 entries
            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(
                Duration.ofHours(1))) // TTL of 1 hour
            .build();

    // Configuration for translation exports cache
    CacheConfiguration<String, Map<String, String>> exportsConfig =
        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class,
                (Class<Map<String, String>>) (Class<?>) Map.class,
                ResourcePoolsBuilder.heap(100)) // 100 entries
            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(
                Duration.ofMinutes(30))) // TTL of 30 minutes
            .build();

    // Configuration for search results cache
    CacheConfiguration<String, List<TranslationResponseDTO>> searchConfig =
        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class,
                (Class<List<TranslationResponseDTO>>) (Class<?>) List.class,
                ResourcePoolsBuilder.heap(500)) // 500 entries
            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(
                Duration.ofMinutes(15))) // TTL of 15 minutes
            .build();

    // Create caches if they don't exist
    if (cacheManager.getCache(TRANSLATIONS_CACHE, Integer.class, TranslationResponseDTO.class) == null) {
      cacheManager.createCache(
          TRANSLATIONS_CACHE,
          Eh107Configuration.fromEhcacheCacheConfiguration(translationsConfig));
    }

    if (cacheManager.getCache(TRANSLATION_EXPORTS_CACHE, String.class, Map.class) == null) {
      cacheManager.createCache(
          TRANSLATION_EXPORTS_CACHE,
          Eh107Configuration.fromEhcacheCacheConfiguration(exportsConfig));
    }

    if (cacheManager.getCache(SEARCH_RESULTS_CACHE, String.class, List.class) == null) {
      cacheManager.createCache(
          SEARCH_RESULTS_CACHE,
          Eh107Configuration.fromEhcacheCacheConfiguration(searchConfig));
    }

    return cacheManager;
  }
}