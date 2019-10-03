package com.demo.crawler.service;

import java.util.HashMap;
import java.util.Map;

public enum CrawlerServiceFieldsEnum {

    Crawler(0),
    Parser(1),
    SiteMapGenerator(2),
    ResponseGenerator(3);

    private final Integer order;

    private CrawlerServiceFieldsEnum(int order) {
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }

    private static Map<String, CrawlerServiceFieldsEnum> valuesMap = new HashMap<>();

    static {
        for (CrawlerServiceFieldsEnum serviceFieldsEnum : CrawlerServiceFieldsEnum.values()) {
            valuesMap.put(serviceFieldsEnum.toString(), serviceFieldsEnum);
        }
    }

    public static CrawlerServiceFieldsEnum getByName(String crawlerServiceName) {
        return valuesMap.get(crawlerServiceName);
    }

}
