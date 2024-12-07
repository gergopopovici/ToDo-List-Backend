package edu.bbte.idde.pgim2289.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class)
            .headless(true)
            .run(args);
    }
}
