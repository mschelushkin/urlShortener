package com.mchelushkin.urlshortener.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.mchelushkin.urlshortener.model.dto.UrlStatistics;
import com.mchelushkin.urlshortener.model.entity.Url;
import com.mchelushkin.urlshortener.model.exception.InvalidUrlException;
import com.mchelushkin.urlshortener.model.repository.UrlRepository;
import lombok.AllArgsConstructor;
import one.util.streamex.EntryStream;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    private final static UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});

    public String createShortUrl(String originUrl) {
        if (!urlValidator.isValid(originUrl)) throw new InvalidUrlException("Got invalid url: " + originUrl);
        Url url = new Url();
        url.setOriginUrl(originUrl);
        urlRepository.save(url);
        return url.getUrl();
    }

    public String getOriginUrlForRedirect(String shortUrl) {
        Url url = urlRepository.findById(shortUrl)
                .orElseThrow(() -> new EntityNotFoundException("Unknown short url:" + shortUrl));
        urlRepository.incrementRedirectsNumber(shortUrl);
        return url.getOriginUrl();
    }

    public List<UrlStatistics> getStatistics(int pageNumber, int count) {
        Page<Url> sortedUrls = urlRepository.findAll(PageRequest
                .of(pageNumber, count, Sort.by("redirectCounter").descending()));
        int baseRating = pageNumber * count + 1;
        return EntryStream.of(sortedUrls.getContent())
                .mapKeys(i -> baseRating + i)
                .mapKeyValue(UrlStatistics::new).toList();
    }

    public UrlStatistics getStatistics(String shortUrl) {
        return EntryStream.of(urlRepository.findAll(Sort.by("redirectCounter").descending()))
                .mapKeys(i -> i + 1)
                .findFirst(url -> url.getValue().getUrl().equals(shortUrl))
                .map(entry -> new UrlStatistics(entry.getKey(), entry.getValue()))
                .orElseThrow(() -> new EntityNotFoundException("Unknown short url:" + shortUrl));
    }

}
