package com.conestoga.APIHousing.service;

import com.google.cloud.functions.Context;
import com.google.cloud.functions.RawBackgroundFunction;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUpdaterService implements RawBackgroundFunction {

    Logger logger = Logger.getLogger(DatabaseUpdaterService.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void accept(String content, Context context) {
        // Retrieve the SQL script content from the Cloud Storage bucket
        String sqlScriptContent = retrieveSqlScriptContent();

        // Update your database with the SQL script content
        updateDatabase(sqlScriptContent);
    }

    // Uncomment it to auto update the database on startup, according to what you wrote
    //@PostConstruct
    private String retrieveSqlScriptContent() {
        // Set your Cloud Storage bucket and file details
        String bucketName = "focus-chain-392022.appspot.com";
        String fileName = "housing.sql";

        // Retrieve the SQL script content from the Cloud Storage bucket
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Blob blob = storage.get(bucketName, fileName);

        // Forcefully modify the database in case Hibernate doesn't do it
        updateDatabase(new String("".getBytes(), StandardCharsets.UTF_8));
        return new String(blob.getContent());
    }

    private void updateDatabase(String content) {
        try {
            // Execute the SQL script
            jdbcTemplate.execute(content);
        } catch (Exception e) {
            // Handle update error
            logger.severe("Error updating database: " + e.getMessage());
        }
    }

}

