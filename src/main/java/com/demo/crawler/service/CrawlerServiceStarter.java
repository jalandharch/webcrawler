package com.demo.crawler.service;

import com.demo.crawler.sitemap.ResponseOutputGenerator;
import com.demo.crawler.sitemap.SiteMapGenerator;
import com.demo.crawler.config.Config;
import com.demo.crawler.parser.HTMLParserService;
import com.demo.crawler.webcrawler.WebCrawlerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CrawlerServiceStarter {
    private static final WebCrawlerService crawlerService = new WebCrawlerService();
    private static final HTMLParserService parserService = new HTMLParserService();
    private static final SiteMapGenerator siteMapGenerator = new SiteMapGenerator();
    private static final ResponseOutputGenerator responseGenerator = new ResponseOutputGenerator();
    private static int SERVICE_THREAD_COUNT = Integer.parseInt(Config.get(Config.SERVICE_STARTER_THREAD_COUNT));
    private static final Logger LOG = LogManager.getLogger(CrawlerServiceStarter.class);

    public static void startCrawlerServices() {

        List<String> crawlerServices = getCrawlerServices();
        ExecutorService executorService = Executors.newFixedThreadPool(SERVICE_THREAD_COUNT);

        CrawlerServiceFactory.getSpecificServicesInOrder(crawlerServices).forEach(service -> {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    service.start();
                }
            });
            LOG.info("Started Service: " + service.getServiceName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        try {
            executorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOG.info("Crawling is done. Stopping all services");
    }

    private static List<String> getCrawlerServices() {
        String servicesValue = Config.get(Config.CRAWLER_SERVICES_LIST);
        List<String> services = new ArrayList<>();
        if (servicesValue == null || servicesValue.isEmpty() || servicesValue.equalsIgnoreCase("all")) {
            LOG.info("Starting all the services");
            return services;
        }
        services = Arrays.stream(servicesValue.split(",")).collect(Collectors.toList());
        if (services.size() > 0) {
            SERVICE_THREAD_COUNT = services.size();
        }
        LOG.info("Starting the services: " + services);
        return services;
    }
}
