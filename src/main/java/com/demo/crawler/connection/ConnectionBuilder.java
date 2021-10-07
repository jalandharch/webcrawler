package com.demo.crawler.connection;

import com.demo.crawler.config.Config;
import com.demo.crawler.utils.HTTPUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


public class ConnectionBuilder {

    private static final boolean randoemUserAgentEnable = Boolean.parseBoolean(Config.get(Config.USER_AGENT_RANDOM_ENABLED));
    private static final Logger LOG = LogManager.getLogger(Config.class);

    public static HttpURLConnection buildConnection(String url) throws IOException {

        HttpURLConnection connection = null;
        URL urlObject = new URL(url);
        connection = (HttpURLConnection) urlObject.openConnection();

        if (connection instanceof HttpsURLConnection) {
            connection = (HttpsURLConnection) urlObject.openConnection();
        }

        connection.setRequestMethod("GET");
        connection.setRequestProperty("charset", "utf-8");
        connection.setDoOutput(true);
        connection.setRequestProperty("User-Agent", Config.get(Config.USER_AGENT));

        if (randoemUserAgentEnable) {
            connection.setRequestProperty("User-Agent", UserAgentSelector.getUserAgent());
            LOG.info("user agent: " + UserAgentSelector.getUserAgent());
        }
//        connection.setRequestProperty("Content-Type", "application/json");

        connection.setConnectTimeout(Integer.parseInt(Config.get(Config.CONNECTION_TIME_OUT)));
        connection.setReadTimeout(Integer.parseInt(Config.get(Config.READ_TIME_OUT)));
        connection.setInstanceFollowRedirects(Boolean.parseBoolean(Config.get(Config.REDIRECTS_ENABLED)));

        return connection;

    }


    public static HttpURLConnection buildConnectionWithParams(String url, HashMap<String, String> params) throws IOException {

        HttpURLConnection connection = buildConnection(url);
        connection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(HTTPUtil.getParamsAsString(params));
        out.flush();
        out.close();
        return connection;

    }

}

