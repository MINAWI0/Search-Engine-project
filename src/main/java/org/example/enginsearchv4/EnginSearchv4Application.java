package org.example.enginsearchv4;

import org.example.enginsearchv4.Utils.DocumentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnginSearchv4Application implements CommandLineRunner {

    @Autowired
    private DocumentUtil documentUtil;

    // Directory path should be provided dynamically or can be hardcoded
    String directoryPath = "/home/minaoui/Documents/web";

    public static void main(String[] args) {
        SpringApplication.run(EnginSearchv4Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Call your method after the application context is fully initialized
        documentUtil.saveDocumentsFromDirectory(directoryPath);
    }

}