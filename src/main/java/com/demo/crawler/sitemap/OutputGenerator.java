package com.demo.crawler.sitemap;

import com.demo.crawler.commons.URLNode;
import com.demo.crawler.config.Config;
import com.demo.crawler.service.CrawlerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class OutputGenerator implements CrawlerService {
    private ConcurrentLinkedQueue<URLNode> outputQueue;
    private String serviceName;
    private String fileName;
    private static final int CRAWLER_MAX_WAITING_TIME = Integer.parseInt(Config.get(Config.CRAWLER_MAX_WAITING_TIME));
    private static final Logger LOG = LogManager.getLogger(OutputGenerator.class);

    public OutputGenerator(ConcurrentLinkedQueue<URLNode> outputQueue, String serviceName, String fileName) {
        this.outputQueue = outputQueue;
        this.serviceName = serviceName;
        this.fileName = fileName;
    }

    @Override
    public void start() {
        int queueEmptyCounter = 0;
        boolean isQueueEmpty = true;

        try (BufferedWriter br = new BufferedWriter(new FileWriter(fileName))) {

            while (true) {
                URLNode urlNode = null;
                isQueueEmpty = true;
                while ((urlNode = outputQueue.poll()) != null) {
                    isQueueEmpty = false;
                    br.write(getContent(urlNode));
                    br.write("\n");
                    br.flush();
                }
                Thread.sleep(10000);
                LOG.info(serviceName + " sleeping for 10 sec as queue is empty: " + Thread.currentThread().getName() + " counter: " + queueEmptyCounter);
                if (isQueueEmpty) {
                    queueEmptyCounter++;
                } else {
                    queueEmptyCounter = 0;
                }

                if (queueEmptyCounter == CRAWLER_MAX_WAITING_TIME) {
                    LOG.info("Shutting down the service: " + serviceName);
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error occured in writing file contents: ", e);
        }
        LOG.info("Exiting the " + serviceName + " service...");

    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    //Made abstract so that children will pass respective content
    public abstract String getContent(URLNode urlNode);
}
