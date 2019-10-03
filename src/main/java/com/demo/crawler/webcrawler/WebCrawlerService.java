package com.demo.crawler.webcrawler;

import com.demo.crawler.commons.CrawlerInfo;
import com.demo.crawler.commons.URLNode;
import com.demo.crawler.service.CrawlerService;
import com.demo.crawler.config.Config;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WebCrawlerService implements CrawlerService {

    private static final int MAX_DEPTH = Integer.parseInt(Config.get(Config.MAX_DEPTH));
    private static final int CRAWLER_MAX_WAITING_TIME = Integer.parseInt(Config.get(Config.CRAWLER_MAX_WAITING_TIME));
    private static final String SERVICE_NAME = "WebCrawlerService";
    private static final Crawler<URLNode> crawler = new WebCrawler();
    private static final int CRAWLER_THREAD_COUNT = Integer.parseInt(Config.get(Config.WEBCRAWLER_SERVICE_THREAD_COUNT));
    private static final Logger LOG = Logger.getLogger(WebCrawlerService.class);

    @Override
    public void start() {
        populateInputUrls();
        ExecutorService executorService = Executors.newFixedThreadPool(CRAWLER_THREAD_COUNT);

        int queueEmptyCounter = 0;
        boolean isQueueEmpty = true;

        while (true) {
//            displayQueueUrls();
            URLNode urlNode = null;
            List<Future<?>> futures = new ArrayList<Future<?>>();
            isQueueEmpty = true;
            while ((urlNode = CrawlerInfo.getInputUrlsQueue().poll()) != null) {
                isQueueEmpty = false;
                if (urlNode.getCurrentDepth() <= MAX_DEPTH) {
                    if (!CrawlerInfo.getVisitedUrls().containsKey(urlNode.getInputUrl())) {
                        LOG.info("Submitting url: " + urlNode.getInputUrl());
                        LOG.info("Visited Urls size: " + CrawlerInfo.getVisitedUrls().size());
                        Future<?> future = executorService.submit(new CrawlerThread(urlNode, crawler));
                        futures.add(future);
                        CrawlerInfo.getVisitedUrls().putIfAbsent(urlNode.getInputUrl(), urlNode.getInputUrl());
                        LOG.info("added url to visisted urls queue: " + urlNode.getInputUrl());
                    } else {
                        LOG.info("The url is already visited: " + urlNode.getInputUrl());
                    }
                } else {
                    LOG.info("Max depth is crossed for the URL: " + urlNode.getInputUrl());
                }
            }
            try {
                Thread.sleep(10000);
                LOG.info("Crawler sleeping for 10 sec as queue is empty: " + Thread.currentThread().getName() + " counter: " + queueEmptyCounter);

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

        LOG.info("Exiting the crawler service for: " + getServiceName());
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }


    private boolean allJobsDone(List<Future<?>> futures) {
        return futures.stream().allMatch(Future::isDone);
    }

    private void displayQueueUrls() {
        LOG.info("Input Queue URLs: ");
        CrawlerInfo.getInputUrlsQueue().iterator().forEachRemaining(r -> LOG.info(r.getInputUrl()));

        LOG.info("Visited Queue URLs: ");
        CrawlerInfo.getVisitedUrls().entrySet().stream().forEach(e -> LOG.info(e.getKey() + " : " + e.getValue()));
    }

    private void populateInputUrls() {
        LOG.info("file name: " + Config.get(Config.INPUT_URLS_FILE));
        try (BufferedReader br = new BufferedReader(new InputStreamReader(WebCrawlerService.class.getClassLoader().getResourceAsStream(Config.get(Config.INPUT_URLS_FILE))))) {
            String line = null;

            while ((line = br.readLine()) != null) {
                CrawlerInfo.getInputUrlsQueue().add(new URLNode(line.trim(), line.trim(), line.trim()));
            }

        } catch (Exception e) {
            throw new RuntimeException("Exception occured while reading input file", e);

        }
    }

}