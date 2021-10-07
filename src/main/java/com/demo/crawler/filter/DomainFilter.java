package com.demo.crawler.filter;

import com.demo.crawler.transform.DomainExtractor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;

public class DomainFilter {
    private static final Logger LOG = LogManager.getLogger(DomainFilter.class);

    public static boolean isaValidDomain(String url, String parentDomain) {
        try {
            String cleanDomain = DomainExtractor.getDomainUrl(url);
            LOG.info("clean url: " + cleanDomain);

            if (cleanDomain != null && cleanDomain.startsWith(parentDomain))
                return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        String url = "http://support.indeep.co.in";
        System.out.println(isaValidDomain(url, "indeep.co.in"));

    }

}
