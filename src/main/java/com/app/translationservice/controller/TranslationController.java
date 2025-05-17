package com.app.translationservice.controller;

import com.app.translationservice.dto.TranslationRequestDTO;
import com.app.translationservice.dto.TranslationResponseDTO;
import com.app.translationservice.service.TranslationService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/translations")
public class TranslationController {

  private final TranslationService translationService;

  @Autowired
  public TranslationController(TranslationService translationService) {
    this.translationService = translationService;
  }

  @PostMapping
  public ResponseEntity<TranslationResponseDTO> create(@RequestBody TranslationRequestDTO dto) {
    TranslationResponseDTO created = translationService.create(dto);
    return ResponseEntity.ok(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TranslationResponseDTO> update(
      @PathVariable Integer id, @RequestBody TranslationRequestDTO dto
  ) {
    TranslationResponseDTO updated = translationService.update(id, dto);
    return ResponseEntity.ok(updated);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TranslationResponseDTO> get(@PathVariable Integer id) {
    TranslationResponseDTO translation = translationService.getById(id);
    return ResponseEntity.ok(translation);
  }

  @GetMapping("/search")
  public ResponseEntity<List<TranslationResponseDTO>> search(
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) Set<String> tags) {
    List<TranslationResponseDTO> results = translationService.search(keyword, tags);
    return ResponseEntity.ok(results);
  }

  @GetMapping("/export/{locale}")
  public ResponseEntity<Map<String, String>> exportJson(@PathVariable String locale) {
    Map<String, String> translations = translationService.exportJson(locale);
    return ResponseEntity.ok(translations);
  }
}
