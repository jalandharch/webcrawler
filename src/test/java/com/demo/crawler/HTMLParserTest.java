package com.demo.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class HTMLParserTest  {


    public static void main(String[] args) {
        File input = new File("/Users/jalandharchinthakunta/Downloads/monzo_page.html");
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "http://monzo.com/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements links = doc.select("a[href]"); // a with href
        links.forEach(r -> System.out.println(r.attr("href")));
        Elements pngs = doc.select("img[src$=.png]");
        // img with src ending .png

        Element masthead = doc.select("div.masthead").first();
        // div with class=masthead

        Elements resultLinks = doc.select("h3.r > a");
    }

    }

