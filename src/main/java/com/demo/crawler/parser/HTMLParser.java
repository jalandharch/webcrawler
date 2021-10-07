package com.demo.crawler.parser;

import com.demo.crawler.commons.URLNode;
import com.demo.crawler.commons.CrawlerInfo;
import com.demo.crawler.filter.DomainFilter;
import com.demo.crawler.transform.URLTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class HTMLParser implements Parser<URLNode> {
    private static final URLTransformer urlTransformer = new URLTransformer();
    private static final Logger LOG = LogManager.getLogger(HTMLParser.class);

    @Override
    public void parse(URLNode response) {
        Document document = HTMLParser.parseHTMLResponse(response.getResponse(), response.getInputUrl());
        if (document != null) {
            Elements links = document.select("a[href]");

            for (Element link : links) {
//                String parsedUrl = link.attr("href");
                String parsedUrl = link.attr("abs:href");
                LOG.info("parsed link from response : " + parsedUrl);
//                LOG.info("absolute parsed link from response : " + absoluteParsedUrl);
                if (!CrawlerInfo.getVisitedUrls().contains(parsedUrl)) {
                    URLNode urlNode = new URLNode(parsedUrl, response.getInputUrl(), response.getRootUrl());
                    urlNode.setCurrentDepth(response.getCurrentDepth() + 1);
                    urlNode.setRootDomain(response.getRootDomain());

                    //Transform the URL
                    if (!urlNode.isTransformed()) {
                        urlTransformer.transform(urlNode);
                    }
                    if (!DomainFilter.isaValidDomain(parsedUrl, response.getRootDomain())) {
                        LOG.info("Invalid domain, skipping the url in parsing: " + parsedUrl);
                        continue;
                    }
                    CrawlerInfo.getInputUrlsQueue().offer(urlNode);
                }
            }
        }

    }


    public static Document parseHTMLResponse(String htmlResponse, String baseUrl) {
        Document document = null;
        boolean valid = Jsoup.isValid(htmlResponse, Whitelist.basic());

        if (valid) {
            LOG.info("The document is valid");
            document = Jsoup.parse(htmlResponse, baseUrl);
        } else {
            LOG.info("The document is not valid..Cleaned document");
            Document dirtyDoc = Jsoup.parse(htmlResponse, baseUrl);
            document = new Cleaner(Whitelist.basic()).clean(dirtyDoc);
//            LOG.info(document.html());
        }
        return document;

    }
}
