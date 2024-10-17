package com.tienphuckx.files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class FileController {
    @Autowired
    private FileStorageService fileStorageService;

    private static final Logger LOGGER = Logger.getLogger(FileController.class.getName());

    @PostMapping("upload-file")
    public boolean uploadFiles(@RequestParam("file") MultipartFile fileToSave) {
        try {
            fileStorageService.doSaveFile(fileToSave);
            return true;
        } catch (IOException exception){
            LOGGER.log(Level.SEVERE, "Error saving file", exception);
        }
        return false;
    }

    @GetMapping("download-file")
    public ResponseEntity<Resource> downloadFile(@RequestParam("file") String fileNameToDownload) {
        LOGGER.log(Level.INFO, "[download-file] Downloading file: " + fileNameToDownload);
        try {
            // Get the path to the file from the file storage service
            Path fileToDownload = fileStorageService.getDownloadFile(fileNameToDownload).toPath();

            // Get the file size and use it for the content length
            long fileSize = Files.size(fileToDownload);

            // Create the InputStreamResource for the file
            InputStreamResource resource = new InputStreamResource(Files.newInputStream(fileToDownload));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileNameToDownload + "\"")
                    .contentLength(fileSize)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Error downloading file", exception);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("download-file-faster")
    public ResponseEntity<Resource> downloadFile_Faster(@RequestParam("file") String fileNameToDownload) {
        LOGGER.log(Level.INFO, "[download-file-faster] Downloading file: " + fileNameToDownload);
        try {
            // Get the path to the file from the file storage service
            Path fileToDownload = fileStorageService.getDownloadFile(fileNameToDownload).toPath();

            // Get the file size and use it for the content length
            long fileSize = Files.size(fileToDownload);

            // Use FileSystemResource to serve the file
            FileSystemResource fileResource = new FileSystemResource(fileToDownload.toFile());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileNameToDownload + "\"")
                    .contentLength(fileSize)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileResource);

        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Error downloading file", exception);
            return ResponseEntity.notFound().build();
        }
    }


}
