package com.demo.crawler.commons;

public class URLNode {
    private String inputUrl;
    private String rootUrl;
    private String rootDomain;
    private String domainUrl;
    private String parentUrl;
    private int currentDepth = 1;
    private String response;
    private boolean isTransformed;

    public String getRootDomain() {
        return rootDomain;
    }

    public void setRootDomain(String rootDomain) {
        this.rootDomain = rootDomain;
    }

    public boolean isTransformed() {
        return isTransformed;
    }

    public void setTransformed(boolean transformed) {
        isTransformed = transformed;
    }


    public URLNode(String inputUrl, String parentUrl, String rootUrl) {
        this.inputUrl = inputUrl;
        this.parentUrl = parentUrl;
        this.rootUrl = rootUrl;
    }

    public URLNode(String inputUrl) {
        this.inputUrl = inputUrl;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getInputUrl() {
        return inputUrl;
    }

    public void setInputUrl(String inputUrl) {
        this.inputUrl = inputUrl;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public String getParentUrl() {
        return parentUrl;
    }

    public void setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
    }

    public int getCurrentDepth() {
        return currentDepth;
    }

    public void setCurrentDepth(int currentDepth) {
        this.currentDepth = currentDepth;
    }

    public void incrementDepth() {
        this.currentDepth++;
    }
}
