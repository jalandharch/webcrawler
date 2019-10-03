package com.demo.crawler.parser;

import com.demo.crawler.commons.CrawlerInfo;
import com.demo.crawler.commons.URLNode;
import com.demo.crawler.service.CrawlerService;
import com.demo.crawler.config.Config;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class HTMLParserService implements CrawlerService {
    private static final int MAX_DEPTH = Integer.parseInt(Config.get(Config.MAX_DEPTH));
    private static final int CRAWLER_MAX_WAITING_TIME = Integer.parseInt(Config.get(Config.CRAWLER_MAX_WAITING_TIME));
    private static final String SERVICE_NAME = "Parser";
    private static final Parser<URLNode> htmlParser = new HTMLParser();
    private static final int PARSER_THREAD_COUNT = Integer.parseInt(Config.get(Config.PARSER_SERVICE_THREAD_COUNT));
    private static final Logger LOG = Logger.getLogger(HTMLParserService.class);

    @Override
    public void start() {
        ExecutorService executorService = Executors.newFixedThreadPool(PARSER_THREAD_COUNT);
        int queueEmptyCounter = 0;
        boolean isQueueEmpty = true;

        while (true) {
            URLNode urlNode = null;
            List<Future<?>> futures = new ArrayList<Future<?>>();
            LOG.info("responseQueue size is: " + CrawlerInfo.getResponseQueue().size());
            isQueueEmpty = true;
            while ((urlNode = CrawlerInfo.getResponseQueue().poll()) != null) {
                isQueueEmpty = false;
                if (urlNode.getCurrentDepth() <= MAX_DEPTH) {
                    Future<?> future = executorService.submit(new ParserThread(urlNode, htmlParser));
                    futures.add(future);
                } else {
                    LOG.info("Max depth is crossed for the URL: " + urlNode.getInputUrl());
                }

            }
            try {
                Thread.sleep(10000);
                LOG.info("Parser sleeping for 10 sec as queue is empty" + Thread.currentThread().getName() + " counter: " + queueEmptyCounter);

                if (isQueueEmpty && allJobsDone(futures)) {
                    queueEmptyCounter++;
                } else {
                    queueEmptyCounter = 0;
                }

                if (queueEmptyCounter == CRAWLER_MAX_WAITING_TIME) {
                    executorService.shutdown();
                    executorService.awaitTermination(1, TimeUnit.MINUTES);
                    LOG.info("Shutting down the executor service: ");
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOG.info("Exiting the parser service...");

    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }


    private boolean allJobsDone(List<Future<?>> futures) {
        return futures.stream().allMatch(Future::isDone);
    }
}