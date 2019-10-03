package com.demo.crawler.commons;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CrawlerInfo {
    private static final ConcurrentHashMap<String, String> visitedUrls = new ConcurrentHashMap<String, String>();
    private static final ConcurrentLinkedQueue<URLNode> inputUrlsQueue = new ConcurrentLinkedQueue<URLNode>();
    private static final ConcurrentLinkedQueue<URLNode> responseQueue = new ConcurrentLinkedQueue<URLNode>();
    private static final ConcurrentLinkedQueue<URLNode> siteMapOutputQueue = new ConcurrentLinkedQueue<URLNode>();
    private static final ConcurrentLinkedQueue<URLNode> responseOutputQueue = new ConcurrentLinkedQueue<URLNode>();

    private static final CrawlerInfo instance = new CrawlerInfo();

    private CrawlerInfo() {

    }

    public CrawlerInfo getInstance() {
        return instance;
    }

    public static ConcurrentHashMap<String, String> getVisitedUrls() {
        return visitedUrls;
    }

    public static ConcurrentLinkedQueue<URLNode> getInputUrlsQueue() {
        return inputUrlsQueue;
    }

    public static ConcurrentLinkedQueue<URLNode> getResponseQueue() {
        return responseQueue;
    }

    public static ConcurrentLinkedQueue<URLNode> getSiteMapOutputQueue() {
        return siteMapOutputQueue;
    }

    public static ConcurrentLinkedQueue<URLNode> getResponseOutputQueue() {
        return responseOutputQueue;
    }
}
