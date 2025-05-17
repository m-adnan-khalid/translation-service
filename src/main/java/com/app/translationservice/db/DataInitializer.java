package com.app.translationservice.db;

import com.app.translationservice.model.Translation;
import com.app.translationservice.repository.TranslationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

  private final TranslationRepository translationRepository;

  public DataInitializer(TranslationRepository translationRepository) {
    this.translationRepository = translationRepository;
  }

  @Override
  public void run(String... args) {
    List<Translation> translations = new ArrayList<>();
    for (int i = 0; i < 100000; i++) {
      String baseKey = "key" + i;
      // English
      translations.add(createTranslation(baseKey, "en", "Welcome message " + i, Set.of("web", "mobile")));
      // French
      translations.add(createTranslation(baseKey, "fr", "Message de bienvenue " + i, Set.of("web", "mobile")));
      // Spanish
      translations.add(createTranslation(baseKey, "es", "Mensaje de bienvenida " + i, Set.of("web", "mobile")));
      if (i % 500 == 0) {
        translationRepository.saveAll(translations);
        translations.clear();
      }
    }
    if (!translations.isEmpty()) {
      translationRepository.saveAll(translations);
    }
  }

  private Translation createTranslation(String key, String locale, String content, Set<String> tags) {
    Translation t = new Translation();
    t.setKey(key);
    t.setLocale(locale);
    t.setContent(content);
    t.setTags(tags);
    return t;
  }
}
