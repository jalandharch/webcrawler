package com.demo.crawler.webcrawler;

import com.demo.crawler.commons.URLNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class CrawlerThread implements Runnable {
    private URLNode urlNode;
    private Crawler<URLNode> webCrawler;
    private static final Random random = new Random();
    private static final Logger LOG = LogManager.getLogger(CrawlerThread.class);

    CrawlerThread(URLNode urlNode, Crawler<URLNode> webCrawler) {
        this.urlNode = urlNode;
        this.webCrawler = webCrawler;
    }

    public void run() {
        webCrawler.crawl(urlNode);
        try {
            Thread.sleep(1000 + random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOG.info("Crawled the url: " + urlNode.getInputUrl() + " in thread: " + Thread.currentThread().getName());

    }
}
