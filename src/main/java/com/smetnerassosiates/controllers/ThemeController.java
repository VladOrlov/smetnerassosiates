package com.smetnerassosiates.controllers;

import com.smetnerassosiates.controllers.enums.Order;
import com.smetnerassosiates.controllers.exceptions.ContentParseException;
import com.smetnerassosiates.controllers.exceptions.WrongURLException;
import com.smetnerassosiates.domain.Theme;
import com.smetnerassosiates.services.ThemeService;
import com.smetnerassosiates.util.ThemeParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RepositoryRestController
public class ThemeController {

  @Autowired
  private ThemeService themeService;

  @Autowired
  private ThemeParser themeParser;

  @ResponseBody
  @GetMapping(value = "/themes/all", produces = "application/hal+json")
  public Iterable<Theme> viewAllThemes(@RequestParam(name = "order", required = false) List<Order> order,
                                       @RequestParam(name = "direction", required = false, defaultValue = "ASC") Sort.Direction direction) {

    if (!CollectionUtils.isEmpty(order)) {
      List<String> fieldsNames = order.stream()
          .map(Order::getFieldName)
          .collect(Collectors.toList());
      return themeService.getThemesSorted(fieldsNames, direction);
    } else {
      return themeService.getThemes();
    }
  }

  @ResponseBody
  @GetMapping(value = "/themes/{id}", produces = "application/hal+json")
  public Theme viewAllThemes(@PathVariable("id") Long id) {
    return themeService.getTheme(id);
  }

  @ResponseBody
  @PostMapping(value = "/themes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/hal+json")
  public PersistentEntityResource addTheme(@RequestBody Theme theme, PersistentEntityResourceAssembler assembler) {
    return assembler.toFullResource(themeService.addTheme(theme));
  }

  @ResponseBody
  @PutMapping(value = "/themes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/hal+json")
  public PersistentEntityResource updateTheme(@PathVariable("id") Long id,
                                              @RequestBody Theme theme, PersistentEntityResourceAssembler assembler) {
    theme.setId(id);
    return assembler.toFullResource(themeService.addTheme(theme));
  }

  @ResponseBody
  @DeleteMapping(value = "/themes/{id}")
  public void deleteTheme(@PathVariable("id") Long id) {
    themeService.removeTheme(id);
  }

  @ResponseBody
  @GetMapping(value = "/themes/fillThemes", produces = "application/hal+json")
  public Iterable<Theme> fillTheme(@RequestParam Map<String, String> requestParams) throws WrongURLException, ContentParseException {
    if (requestParams.get("uri") == null) {
      throw new WrongURLException("URI not defined!");
    }

    List<Theme> parsedThemes = themeParser.parseThemes(requestParams.get("uri"));
    if (CollectionUtils.isEmpty(parsedThemes)) {
      throw new ContentParseException("Fail to parse page content!");
    }

    return themeService.persistThemes(parsedThemes);
  }

}
