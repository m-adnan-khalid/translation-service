package com.app.translationservice.service.impl;

import com.app.translationservice.dto.TranslationRequestDTO;
import com.app.translationservice.dto.TranslationResponseDTO;
import com.app.translationservice.exception.NotFoundException;
import com.app.translationservice.model.Translation;
import com.app.translationservice.repository.TranslationRepository;
import com.app.translationservice.service.TranslationService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.cache.annotation.CacheResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@CacheConfig(cacheNames = {"translations", "translationExports", "searchResults"})
public class TranslationServiceImpl implements TranslationService {

  private final TranslationRepository translationRepository;

  @Autowired
  public TranslationServiceImpl(TranslationRepository translationRepository) {
    this.translationRepository = translationRepository;
  }

  @Override
  @CacheEvict(cacheNames = {"translations", "translationExports",
      "searchResults"}, allEntries = true)
  public TranslationResponseDTO create(TranslationRequestDTO dto) {
    Translation translation = mapToEntity(dto);
    return mapToDto(translationRepository.save(translation));
  }

  @Override
  @CachePut(value = "translations", key = "#id")
  @CacheEvict(cacheNames = {"translationExports", "searchResults"}, allEntries = true)
  public TranslationResponseDTO update(Integer id, TranslationRequestDTO dto) {
    Translation translation = translationRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Translation not found with id: " + id));

    translation.setLocale(dto.getLocale());
    translation.setKey(dto.getKey());
    translation.setContent(dto.getContent());
    translation.setTags(dto.getTags() != null ? dto.getTags() : new HashSet<>());

    return mapToDto(translationRepository.save(translation));
  }

  @Override
  @Cacheable(value = "translations", key = "#id")
  public TranslationResponseDTO getById(Integer id) {
    Translation translation = translationRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Translation not found with id: " + id));
    return mapToDto(translation);
  }

  @Override
  @CacheResult(cacheName = "searchResults")
  public List<TranslationResponseDTO> search(String keyword, Set<String> tags) {
    if ((keyword == null || keyword.isBlank()) && (tags == null || tags.isEmpty())) {
      return new ArrayList<>();
    }
    Set<Translation> results = new HashSet<>();
    if (keyword != null && !keyword.isBlank()) {
      results.addAll(translationRepository.findByKeyContainingIgnoreCase(keyword));
      results.addAll(translationRepository.findByContentContainingIgnoreCase(keyword));
    }
    if (tags != null && !tags.isEmpty()) {
      results.addAll(translationRepository.findByTagsIn(tags));
    }
    return results.stream()
        .map(this::mapToDto)
        .toList();
  }

  @Override
  @CacheResult(cacheName = "translationExports")
  public Map<String, String> exportJson(String locale) {
    // Use pagination to handle large datasets
    int page = 0;
    int size = 1000;
    Map<String, String> result = new HashMap<>();
    Pageable pageable = PageRequest.of(page, size);
    Page<Translation> translationPage;
    do {
      translationPage = translationRepository.findByLocale(locale, pageable);
      translationPage.getContent().forEach(t -> result.put(t.getKey(), t.getContent()));
      pageable = PageRequest.of(++page, size);
    } while (translationPage.hasNext());

    return result;
  }

  private Translation mapToEntity(TranslationRequestDTO dto) {
    Translation translation = new Translation();
    translation.setLocale(dto.getLocale());
    translation.setKey(dto.getKey());
    translation.setContent(dto.getContent());
    translation.setTags(dto.getTags() != null ? dto.getTags() : new HashSet<>());
    return translation;
  }

  private TranslationResponseDTO mapToDto(Translation entity) {
    TranslationResponseDTO dto = new TranslationResponseDTO();
    dto.setId(entity.getId());
    dto.setLocale(entity.getLocale());
    dto.setKey(entity.getKey());
    dto.setContent(entity.getContent());
    dto.setTags(entity.getTags());
    return dto;
  }
}