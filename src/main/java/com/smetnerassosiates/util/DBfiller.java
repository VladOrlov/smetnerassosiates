package com.smetnerassosiates.util;

import com.smetnerassosiates.domain.Theme;
import com.smetnerassosiates.repositories.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DBfiller {

    @Autowired
    private ThemeRepository themeRepository;

    public void fill() {
        List<Theme> themes = new ArrayList<>();
        themes.add(new Theme("Tecko", "Firefox 3.0", "Win 2k+ / OSX.3+", "1.9",	"A"));
        themes.add(new Theme("Gecko", "Chrome", "Win 2k+ / OSX.3+", "1.9",	"A"));
        themes.add(new Theme("Acko", "Safari", "Mac OS", "1.9",	"A"));
        themeRepository.save(themes);
    }
}
