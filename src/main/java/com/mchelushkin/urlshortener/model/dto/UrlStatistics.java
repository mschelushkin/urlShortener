package com.mchelushkin.urlshortener.model.dto;

import com.mchelushkin.urlshortener.model.entity.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UrlStatistics {

    private static final String LINK_PREFIX = "/l/";

    public UrlStatistics(long rank, Url url) {
        this.link = LINK_PREFIX + url.getUrl();
        this.original = url.getOriginUrl();
        this.redirects = url.getRedirectCounter();
        this.rank = rank;
    }

    private String link;

    private String original;

    private long rank;

    private long redirects;

}
