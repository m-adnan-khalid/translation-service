package com.app.translationservice.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Entity class representing a translation in the system.
 */
@Entity
@Table(
    name = "translations",
    indexes = {
        @Index(name = "idx_locale", columnList = "locale"),
        @Index(name = "idx_translation_key", columnList = "key")
    }
)
public class Translation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String locale;

  @Column(name = "translation_key", nullable = false)
  private String key;

  @Column(nullable = false)
  private String content;

  @ElementCollection
  @CollectionTable(
      name = "translation_tags",
      joinColumns = @JoinColumn(name = "translation_id")
  )
  @Column(name = "tag")
  private Set<String> tags = new HashSet<>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Set<String> getTags() {
    return tags;
  }

  public void setTags(Set<String> tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("locale", locale)
        .append("key", key)
        .append("content", content)
        .append("tags", tags)
        .toString();
  }
}