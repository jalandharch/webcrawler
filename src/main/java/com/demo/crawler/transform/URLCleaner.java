package com.demo.crawler.transform;

import com.demo.crawler.commons.URLNode;

public class URLCleaner implements Transformer<URLNode> {
    @Override
    public void transform(URLNode urlNode) {
        urlNode.setInputUrl(getCleanedUrl(urlNode.getInputUrl()));

    }

    private String getCleanedUrl(String inputUrl) {
        return inputUrl.trim().replaceAll(" ", "");
    }
}
