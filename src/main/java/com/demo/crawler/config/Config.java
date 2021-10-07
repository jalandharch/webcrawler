package com.demo.crawler.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Logger LOG = LogManager.getLogger(Config.class);

    private static final Properties properties = new Properties();
    private static final String PROPERTIES_FILE = "config.properties";

    public static final String USER_AGENT = "user.agent";
    public static final String CONNECTION_TIME_OUT = "connection.timeout";
    public static final String READ_TIME_OUT = "read.timeout";
    public static final String REDIRECTS_ENABLED = "url.redirects.enabled";
    public static final String INPUT_URLS_FILE = "input.urls.file.name";
    public static final String MAX_DEPTH = "crawler.max.depth";
    public static final String CRAWLER_MAX_WAITING_TIME = "crawler.max.waiting.time";
    public static final String RESPONSE_GENERATION_SERVICE_NAME = "response.output.generation.service.name";
    public static final String SITEMAP_GENERATION_SERVICE_NAME = "sitemap.output.generation.service.name";
    public static final String RESPONSE_GENERATION_OUTPUT_FILE_NAME = "response.output.generation.file.name";
    public static final String SITEMAP_GENERATION_OUPUT_FILE_NAME = "sitemap.output.generation.file.name";

    public static final String SERVICE_STARTER_THREAD_COUNT = "service.starter.thread.count";
    public static final String WEBCRAWLER_SERVICE_THREAD_COUNT = "webcrawler.service.thread.count";
    public static final String PARSER_SERVICE_THREAD_COUNT = "parser.service.thread.count";
    public static final String USER_AGENTS_FILE_NAME = "user.agents.file.name";
    public static final String USER_AGENT_RANDOM_ENABLED = "user.agent.random.enabled";
    public static final String CRAWLER_SERVICES_LIST = "crawler.services.list";


    private Config() {

    }

    static {
        loadProperties(PROPERTIES_FILE);
    }

    private static void loadProperties(String propertyFile) {
        InputStream is = null;
        is = Config.class.getClassLoader().getResourceAsStream(propertyFile);
        Properties props = new Properties();
        try {
            props.load(is);
            properties.putAll(props);
        } catch (Exception e) {
            LOG.error("error occured while loading config properties", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static String get(String property) {
        return (String) properties.get(property);
    }


}
