package com.smetnerassosiates.services;

import com.smetnerassosiates.domain.Theme;
import com.smetnerassosiates.repositories.ThemeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {

  @Autowired
  private ThemeRepository themeRepository;

  public Theme addTheme(Theme theme) {
    return themeRepository.save(theme);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Iterable<Theme> getThemes() {
    return themeRepository.findAll();
  }

  public Iterable<Theme> persistThemes(Iterable<Theme> themes) {
    return themeRepository.saveAll(themes);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Theme getTheme(Long id) {
    Optional<Theme> themeOptional = themeRepository.findById(id);
    return themeOptional.orElseThrow(RuntimeException::new);
  }

  public void removeTheme(Long id) {
    themeRepository.deleteById(id);
  }

  public Iterable<Theme> getThemesSorted(List<String> fieldsNames, Sort.Direction direction) {
    String[] fields = fieldsNames.toArray(new String[fieldsNames.size()]);
    return themeRepository.findAll(Sort.by(Sort.Direction.DESC, fields));
  }
}
