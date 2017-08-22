package com.smetnerassosiates.util;


import static org.assertj.core.api.Assertions.assertThat;

import com.smetnerassosiates.domain.Theme;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class ThemeParserTest {

  private static final String TEST_URL = "https://test.com";

  @Test
  public void parseThemes() throws Exception {
    Integer expectedThemesNumber = 57;
    Integer expectedWindowsNumber = 31;

    Document document = getResourceAsDocument("themes.html");

    ThemeParser themeParser = Mockito.spy(new ThemeParser());
    Mockito.doReturn(document).when(themeParser).getContent(TEST_URL);

    List<Theme> themes = themeParser.parseThemes(TEST_URL);

    assertThat(themes.size()).isEqualTo(expectedThemesNumber);

    List<Theme> windowsThemes = themes.stream()
        .filter(Theme::isWindowsCapable)
        .collect(Collectors.toList());

    assertThat(windowsThemes.size()).isEqualTo(expectedWindowsNumber);
  }

  private String getResource(String resourceName) throws IOException {
    return IOUtils.toString(this.getClass().getResourceAsStream(resourceName));
  }

  private Document getResourceAsDocument(String resourceName) {
    Document document = null;
    try {
      document = Jsoup.parse(getResource(resourceName));
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
    return document;
  }

}