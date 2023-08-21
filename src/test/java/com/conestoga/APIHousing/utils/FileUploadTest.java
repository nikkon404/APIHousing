package com.conestoga.APIHousing.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class FileUploadTest {
    @Test
    void testConvertBase64ToFile() throws IOException {
        // Replace this with a valid base64 encoded image string
        String base64String = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";

        // Call the method to convert base64 to file
        String relativeFilePath = FileUpload.convertBase64ToFile(base64String);
        // Check if the file was created and saved correctly
        File savedFile = new File(relativeFilePath);
        Assertions.assertNotNull(savedFile);
        Assertions.assertTrue(savedFile.exists());
        Assertions.assertTrue(savedFile.isFile());

        // Clean up: Delete the created file after the test
        //savedFile.delete();
    }
}
