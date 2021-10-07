package com.demo.crawler.webcrawler;

import com.demo.crawler.commons.URLNode;
import com.demo.crawler.connection.ConnectionBuilder;
import com.demo.crawler.commons.CrawlerInfo;
import com.demo.crawler.filter.DomainFilter;
import com.demo.crawler.transform.URLTransformer;
import com.demo.crawler.utils.HTTPUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;

public class WebCrawler implements Crawler<URLNode> {
    private static final URLTransformer urlTransformer = new URLTransformer();
    private static final Logger LOG = LogManager.getLogger(WebCrawler.class);

    @Override
    public void crawl(URLNode urlNode) {
        HttpURLConnection connection = null;
        String inputUrl = urlNode.getInputUrl();
        try {
            //Transform the URL Before crawling
            if (!urlNode.isTransformed()) {
                urlTransformer.transform(urlNode);
            }

            if (!DomainFilter.isaValidDomain(inputUrl, urlNode.getDomainUrl())) {
                LOG.info("Invalid domain, skipping the crawling: " + inputUrl);
                return;
            }

            LOG.info("Crawling url: " + inputUrl);
            connection = ConnectionBuilder.buildConnection(inputUrl);

            LOG.info("response code: " + connection.getResponseCode() + " for url: " + urlNode.getInputUrl());
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String response = HTTPUtil.getResponseFromInputStream(connection.getInputStream());
                urlNode.setResponse(response);
                CrawlerInfo.getResponseQueue().offer(urlNode);
                CrawlerInfo.getSiteMapOutputQueue().offer(urlNode);
                CrawlerInfo.getResponseOutputQueue().offer(urlNode);

            } else {
                LOG.info("Url could not be crawled: " + inputUrl + " error: " + HTTPUtil.getResponseFromInputStream(connection.getErrorStream()));
            }

        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("Error occured while crawling url: " + urlNode.getInputUrl(), e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}