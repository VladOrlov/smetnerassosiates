package com.smetnerassosiates.controllers;

import com.smetnerassosiates.controllers.exceptions.ContentParseException;
import com.smetnerassosiates.controllers.exceptions.WrongURLException;
import com.smetnerassosiates.domain.Theme;
import com.smetnerassosiates.services.ThemeService;
import com.smetnerassosiates.util.ThemeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RepositoryRestController
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ThemeParser themeParser;

    @ResponseBody
    @RequestMapping(value = "/themes/viewThemes", method = RequestMethod.GET, produces = "application/hal+json")
    public Iterable<Theme> viewAllThemes() {
        return themeService.getThemes();
    }

    @ResponseBody
    @RequestMapping(value = "/themes/viewTheme/{id}", method = RequestMethod.GET, produces = "application/hal+json")
    public Theme viewAllThemes(@PathVariable("id") Long id) {
        return themeService.getTheme(id);
    }

    @ResponseBody
    @RequestMapping(value = "/themes/viewThemesEngineOrder", method = RequestMethod.GET, produces = "application/hal+json")
    public Iterable<Theme> viewAllThemesOrderedByEngine() {
        return themeService.getThemesSorted();
    }

    @ResponseBody
    @RequestMapping(value = "/themes/addTheme", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/hal+json")
    public PersistentEntityResource addTheme(@RequestBody Theme theme, PersistentEntityResourceAssembler assembler) {
        return assembler.toFullResource(themeService.addTheme(theme));
    }

    @ResponseBody
    @RequestMapping(value = "/themes/{id}/updateTheme", method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/hal+json")
    public PersistentEntityResource updateTheme(@PathVariable("id") Long id,
                                                @RequestBody Theme theme, PersistentEntityResourceAssembler assembler) {
        theme.setId(id);
        return assembler.toFullResource(themeService.addTheme(theme));
    }

    @ResponseBody
    @RequestMapping(value = "/themes/{id}/deleteTheme", method = RequestMethod.DELETE, produces = "application/hal+json")
    public PersistentEntityResource deleteTheme(@PathVariable("id") Long id, PersistentEntityResourceAssembler assembler) {
        return assembler.toFullResource(themeService.removeTheme(id));
    }

    @ResponseBody
    @RequestMapping(value = "/themes/fillThemes", method = RequestMethod.GET, produces = "application/hal+json")
    public Iterable<Theme> fillTheme(@RequestParam Map<String, String> requestParams) throws WrongURLException, ContentParseException{
        if(requestParams.get("uri") == null){
            throw new WrongURLException("URI not defined!");
        }

        List<Theme> parsedThemes = themeParser.parseThemes(requestParams.get("uri"));

        if(parsedThemes == null || parsedThemes.size() == 0){
            throw new ContentParseException("Can't parse content!");
        }

        return themeService.persistThemes(parsedThemes);
    }


}
