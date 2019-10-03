package com.demo.crawler.transform;

import com.demo.crawler.commons.URLNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class URLTransformFactory {

    public URLTransformFactory() {
    }

    public static List<Transformer<URLNode>> getAllTransformersInOrder() {
        List<Transformer<URLNode>> transformers = new ArrayList<Transformer<URLNode>>();
        List<URLTransformFieldsEnum> values = Arrays.asList(URLTransformFieldsEnum.values());
        values.forEach(value -> {
            switch (value) {
                case Clean:
                    transformers.add(new URLCleaner());
                    break;
                case Domain:
                    transformers.add(new DomainExtractor());
                    break;
                default:
                    throw new IllegalArgumentException("Illegal Argument for field : " + value);
            }
        });

        return transformers;
    }

}
