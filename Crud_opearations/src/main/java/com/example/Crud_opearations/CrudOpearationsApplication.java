package com.example.Crud_opearations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class CrudOpearationsApplication implements CommandLineRunner {

	@Autowired
	DataSource dataSource;
	public static void main(String[] args) {
		SpringApplication.run(CrudOpearationsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try (Connection connection = dataSource.getConnection()) {
			if (connection != null && !connection.isClosed()) {
				System.out.println("Database connected successfully");
			} else {
				System.out.println("Failed to connect to the database.");
			}
		} catch (Exception e) {
			System.err.println("An error occurred while checking the database connection: " + e.getMessage());
		}
	}
}
