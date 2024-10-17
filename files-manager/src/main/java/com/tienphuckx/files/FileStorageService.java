package com.tienphuckx.files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService {
    static final String STORAGE_DIRECTORY = "D:\\__TEMP\\springboot\\file_storage";

    public void doSaveFile(MultipartFile fileToSave) throws IOException {
        if(fileToSave == null) {
            throw new NullPointerException("Upload file is null");
        }

        //open a file with the same name of the upcoming name
        // we can custom the file name here
        var targetFile = new File(STORAGE_DIRECTORY + File.separator + fileToSave.getOriginalFilename());

        //security validate
        if(!Objects.equals(targetFile.getParent(), STORAGE_DIRECTORY)) {
            throw new SecurityException("Unsupported file name!");
        }

        //copy content of the upcoming file to the target file
        Files.copy(fileToSave.getInputStream(),
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
    }

    public File getDownloadFile(String filename) throws FileNotFoundException {
        //the file name can not be null
        if(filename == null) {
            throw new NullPointerException("Download file is null");
        }

        //get the file from the path
        var fileToDownload = new File(STORAGE_DIRECTORY + File.separator + filename);

        // security validate
        if(!Objects.equals(fileToDownload.getParent(), STORAGE_DIRECTORY)) {
            throw new SecurityException("Unsupported file name!");
        }

        // the file want to download must exist
        if(!fileToDownload.exists()) {
            throw new FileNotFoundException("File not found!" + fileToDownload);
        }
        return fileToDownload;
    }

    public File getDownloadFile_Faster(String filename) throws FileNotFoundException {
        //the file name can not be null
        if(filename == null) {
            throw new NullPointerException("Download file is null");
        }

        //get the file from the path
        var fileToDownload = new File(STORAGE_DIRECTORY + File.separator + filename);

        // security validate
        if(!Objects.equals(fileToDownload.getParent(), STORAGE_DIRECTORY)) {
            throw new SecurityException("Unsupported file name!");
        }

        // the file want to download must exist
        if(!fileToDownload.exists()) {
            throw new FileNotFoundException("File not found!" + fileToDownload);
        }
        return fileToDownload;
    }
}
