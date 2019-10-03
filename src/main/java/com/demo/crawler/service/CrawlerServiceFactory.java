package com.demo.crawler.service;

import com.demo.crawler.sitemap.ResponseOutputGenerator;
import com.demo.crawler.sitemap.SiteMapGenerator;
import com.demo.crawler.parser.HTMLParserService;
import com.demo.crawler.webcrawler.WebCrawlerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CrawlerServiceFactory {


    public CrawlerServiceFactory() {
    }

    public static List<CrawlerService> getAllServicesInOrder() {
        List<CrawlerService> services = new ArrayList<CrawlerService>();
        List<CrawlerServiceFieldsEnum> values = Arrays.asList(CrawlerServiceFieldsEnum.values());
        values.forEach(value -> {
            switch (value) {
                case Crawler:
                    services.add(new WebCrawlerService());
                    break;
                case Parser:
                    services.add(new HTMLParserService());
                    break;
                case SiteMapGenerator:
                    services.add(new SiteMapGenerator());
                    break;
                case ResponseGenerator:
                    services.add(new ResponseOutputGenerator());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Service : " + value);
            }
        });

        return services;
    }

    public static List<CrawlerService> getSpecificServicesInOrder(List<String> serviceNames) {
        //If serviceNames is blank or null return all default services

        if (serviceNames == null || serviceNames.isEmpty()) {
            return getAllServicesInOrder();
        }
        List<CrawlerServiceFieldsEnum> values = serviceNames.stream().map(service -> CrawlerServiceFieldsEnum.getByName(service)).collect(Collectors.toList());
        List<CrawlerService> services = new ArrayList<CrawlerService>();
        values.forEach(value -> {
            switch (value) {
                case Crawler:
                    services.add(new WebCrawlerService());
                    break;
                case Parser:
                    services.add(new HTMLParserService());
                    break;
                case SiteMapGenerator:
                    services.add(new SiteMapGenerator());
                    break;
                case ResponseGenerator:
                    services.add(new ResponseOutputGenerator());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Service : " + value);
            }
        });

        return services;
    }

    public static void main(String[] args) {
        List<String> services = new ArrayList<>();
        services.add("Crawler");
        services.add("Parser");
        getSpecificServicesInOrder(services).forEach(r -> System.out.println(r.getServiceName()));
    }

}
