package com.demo.crawler.sitemap;

import com.demo.crawler.commons.URLNode;
import com.demo.crawler.commons.CrawlerInfo;
import com.demo.crawler.config.Config;

public class ResponseOutputGenerator extends OutputGenerator {
    private static final String serviceName = Config.get(Config.RESPONSE_GENERATION_SERVICE_NAME);
    private static final String fileName = Config.get(Config.RESPONSE_GENERATION_OUTPUT_FILE_NAME);

    public ResponseOutputGenerator() {
        super(CrawlerInfo.getResponseOutputQueue(), serviceName, fileName);
    }

    public String getContent(URLNode urlNode) {
        return urlNode.getResponse();
    }
}
