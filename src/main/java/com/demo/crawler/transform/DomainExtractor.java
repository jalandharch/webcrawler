package com.demo.crawler.transform;

import com.demo.crawler.commons.URLNode;

import java.net.MalformedURLException;
import java.net.URL;

public class DomainExtractor implements Transformer<URLNode> {

    @Override
    public void transform(URLNode urlNode) {
        try {
            urlNode.setDomainUrl(getDomainUrl(urlNode.getInputUrl()));
            if (urlNode.getRootDomain() == null && urlNode.getRootUrl() != null) {
                urlNode.setRootDomain(getDomainUrl(urlNode.getRootUrl()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getDomainUrl(String website) throws MalformedURLException {
        if (website == null)
            return "";
        URL urlObj = null;
        if (!website.startsWith("http"))
            website = "http://" + website;
        urlObj = new URL(website);
        website = urlObj.getHost();
        if (website != null) {
            if (website.startsWith("www.")) {
                website = website.substring(4, website.length());
            } else if (website.startsWith("www1.") || website.startsWith("www2.") || website.startsWith("www3.")) {
                website = website.substring(5, website.length());
            }
        }
        return website.trim();
    }
}
