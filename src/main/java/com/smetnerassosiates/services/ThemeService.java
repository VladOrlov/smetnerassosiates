package com.smetnerassosiates.services;

import com.smetnerassosiates.domain.Theme;
import com.smetnerassosiates.repositories.ThemeRepository;
import com.smetnerassosiates.util.ThemeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Iterable<Theme> persistThemes(Iterable<Theme> themes){
        return themeRepository.save(themes);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Theme getTheme(Long id){
        return themeRepository.findOne(id);
    }

    public Theme removeTheme(Long id){
        Theme theme = themeRepository.findOne(id);
        themeRepository.delete(id);
        return theme;
    }

    public Iterable<Theme> getThemesSorted(){
        return themeRepository.findAll(new Sort(new Sort.Order("renderingEngine")));
    }
}
