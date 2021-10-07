package com.demo.crawler.parser;

import com.demo.crawler.commons.URLNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParserThread implements Runnable {
    private static final Logger LOG = LogManager.getLogger(ParserThread.class);
    private URLNode response;
    private Parser<URLNode> htmlParser;

    public ParserThread(URLNode response, Parser<URLNode> htmlParser) {
        this.response = response;
        this.htmlParser = htmlParser;
    }

    public void run() {
        LOG.info("Parsing : " + response.getInputUrl());
        htmlParser.parse(response);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOG.info("Crawled the url: " + " in thread: " + Thread.currentThread().getName());
    }


}
