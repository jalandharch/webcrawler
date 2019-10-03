package com.demo.crawler.connection;

import com.demo.crawler.config.Config;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserAgentSelector {
    private static List<String> userAgents = new ArrayList<>();
    private static final String USER_AGENTS_FILE = Config.get(Config.USER_AGENTS_FILE_NAME);
    private static final Random random = new Random();
    private static final Logger LOG = Logger.getLogger(UserAgentSelector.class);


    private UserAgentSelector() {
    }

    static {
        loadUserAgents();
    }

    public static String getUserAgent() {
        return userAgents.get(random.nextInt(userAgents.size() - 1));
    }

    private static void loadUserAgents() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(UserAgentSelector.class.getClassLoader().getResourceAsStream(USER_AGENTS_FILE)))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                userAgents.add(line.trim());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception occured while loading user Agents: ", e);
        }
        LOG.info("Successfully loaded user agents: " + userAgents.size());

    }

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            System.out.println(UserAgentSelector.getUserAgent());

        }
    }


}
