package com.conestoga.APIHousing.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.logging.Logger;

public class FileUpload {
  static Logger logger = Logger.getLogger(FileUpload.class.getName());
  // Method that takes in a base64 string and converts it to a file and returns the relative file
  // path
  public static String convertBase64ToFile(String base64String) throws IOException {
    final String publicFolder = "uploads";
    // if the base64 string is an url path like upload, return the url
   if(base64String.contains("uploads/") || base64String.contains("http://") || base64String.contains("https://")){
            return base64String;
        }
    // Decode the base64 string to bytes
    byte[] bytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64String);
    final String staticFolder = "static/" + publicFolder;

    // Define the directory where you want to save the file (using the user's home directory)
    File uploadDir = new File(staticFolder);
    if (!uploadDir.exists()) {
      uploadDir.mkdirs();
    }
    // Create the file with the given file name in the upload directory
    String fileName = generateRandomFileName();
    File file = new File(uploadDir, fileName);

    try (OutputStream outputStream = new FileOutputStream(file)) {
      // Write the bytes to the file
      outputStream.write(bytes);
    } catch (IOException e) {
      logger.warning("Failed to save the file: " + fileName);
      throw new IOException("Failed to save the file: " + fileName, e);
    }

    final String finalFile = publicFolder + File.separator + fileName;
    logger.info("File saved as :" + finalFile);
    return finalFile;
  }

  private static String generateRandomFileName() {
    UUID uuid = UUID.randomUUID();
    String uuidString = uuid.toString().replaceAll("-", "");

    // Ensure the uuidString is at least 32 characters long
    if (uuidString.length() >= 32) {
      return uuidString.substring(0, 32) + ".png";
    } else {
      // Handle the case when uuidString is too short (unlikely to happen)
      return uuidString + ".png";
    }
  }





  private FileUpload() {
  }
}
