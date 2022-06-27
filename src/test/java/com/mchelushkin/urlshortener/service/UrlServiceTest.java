package com.mchelushkin.urlshortener.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.mchelushkin.urlshortener.model.entity.Url;
import com.mchelushkin.urlshortener.model.exception.InvalidUrlException;
import com.mchelushkin.urlshortener.model.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    private UrlService urlService;

    private UrlRepository urlRepository;

    @BeforeEach
    void init(@Mock UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
        urlService = new UrlService(urlRepository);
    }

    @Test
    void createShortUrl_validationNotPassed() {
        assertThrows(InvalidUrlException.class, () -> urlService.createShortUrl("wrongUrlFormat.com"));
    }

    @Test
    void createShortUrl_validationPassed() {
        assertDoesNotThrow(() -> urlService.createShortUrl("https://yandex.ru/"));
    }

    @Test
    void getOriginUrlForRedirect_urlNotFound() {
        Mockito.when(urlRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> urlService.getOriginUrlForRedirect("shortUrl"));
    }

    @Test
    void getOriginUrlForRedirect_urlFound() {
        Mockito.when(urlRepository.findById(any())).thenReturn(Optional.of(new Url()));
        urlService.getOriginUrlForRedirect("shortUrl");
        Mockito.verify(urlRepository, Mockito.times(1)).incrementRedirectsNumber("shortUrl");
    }

}
