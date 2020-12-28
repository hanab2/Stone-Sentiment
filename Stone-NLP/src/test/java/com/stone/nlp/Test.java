package com.stone.nlp;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class Test {
    public static void main(String[] args) {
        URL url = Test.class.getClassLoader().getResource("data");
        System.out.println(url);
        File file = new File(url.getPath());
        System.out.println(file);
    }
}
