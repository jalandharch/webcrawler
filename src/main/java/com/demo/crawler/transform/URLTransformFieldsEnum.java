package com.demo.crawler.transform;

public enum URLTransformFieldsEnum {

    Clean(0),
    Domain(1);

    private final Integer order;

    private URLTransformFieldsEnum(int order) {
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }

}
