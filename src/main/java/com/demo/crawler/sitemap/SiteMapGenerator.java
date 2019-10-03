package com.demo.crawler.sitemap;

import com.demo.crawler.commons.URLNode;
import com.demo.crawler.commons.CrawlerInfo;
import com.demo.crawler.config.Config;

public class SiteMapGenerator extends OutputGenerator {
    private static final String serviceName = Config.get(Config.SITEMAP_GENERATION_SERVICE_NAME);
    private static final String fileName = Config.get(Config.SITEMAP_GENERATION_OUPUT_FILE_NAME);

    public SiteMapGenerator() {
        super(CrawlerInfo.getSiteMapOutputQueue(), serviceName, fileName);
    }


    public String getContent(URLNode urlNode) {
        return "Level:" + urlNode.getCurrentDepth() + "  -->  " + urlNode.getInputUrl();
    }
}
