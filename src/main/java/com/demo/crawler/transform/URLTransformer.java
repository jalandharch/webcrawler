package com.demo.crawler.transform;

import com.demo.crawler.commons.URLNode;

public class URLTransformer implements Transformer<URLNode> {
    public void transform(URLNode urlNode) {
        URLTransformFactory.getAllTransformersInOrder().forEach(r -> r.transform(urlNode));
        urlNode.setTransformed(Boolean.TRUE);
    }
}
