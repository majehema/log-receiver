package com.example.logReceiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 * Main class
 */
@SpringBootApplication
public class LogReceiverApplication {
	public static final String LOG_SAVER = "./src/main/python/logSaver.py";

	public static void main(String[] args) throws Exception {
		File archivo = new File(LOG_SAVER);
		if (!archivo.exists()) {
			throw new Exception("File " + LOG_SAVER + " does not exist");
		}

		SpringApplication.run(LogReceiverApplication.class, args);
	}

}
