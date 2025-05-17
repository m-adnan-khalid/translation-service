package com.app.translationservice.service;

import com.app.translationservice.dto.TranslationRequestDTO;
import com.app.translationservice.dto.TranslationResponseDTO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TranslationService {

  TranslationResponseDTO create(TranslationRequestDTO dto);

  TranslationResponseDTO update(Integer id, TranslationRequestDTO dto);

  TranslationResponseDTO getById(Integer id);

  List<TranslationResponseDTO> search(String keyword, Set<String> tags);

  Map<String, String> exportJson(String locale);

}
