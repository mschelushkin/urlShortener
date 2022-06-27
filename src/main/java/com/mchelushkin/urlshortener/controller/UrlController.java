package com.mchelushkin.urlshortener.controller;

import java.util.Collections;
import java.util.List;

import com.mchelushkin.urlshortener.model.dto.GenerateRequest;
import com.mchelushkin.urlshortener.model.dto.UrlStatistics;
import com.mchelushkin.urlshortener.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@AllArgsConstructor
public class UrlController {

    UrlService urlService;

    @PostMapping("/generate")
    String generateShortUrl(@RequestBody GenerateRequest response) {
        return urlService.createShortUrl(response.getOriginal());
    }

    @GetMapping("/l/{shortUrl}")
    ModelAndView redirect(@PathVariable String shortUrl) {
        String originUrl = urlService.getOriginUrlForRedirect(shortUrl);
        return new ModelAndView("redirect:" + originUrl);
    }

    @GetMapping("/stats")
    List<UrlStatistics> getStatistics(@RequestParam int page, @RequestParam int count) {
        if (page <= 0 || count <= 1) return Collections.emptyList();
        return urlService.getStatistics(page - 1, count);
    }

    @GetMapping("/stats/{url}")
    UrlStatistics getStatistics(@PathVariable String url) {
        return urlService.getStatistics(url);
    }

}
