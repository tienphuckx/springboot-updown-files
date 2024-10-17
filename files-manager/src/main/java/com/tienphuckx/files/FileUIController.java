package com.tienphuckx.files;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.tienphuckx.files.FileStorageService.STORAGE_DIRECTORY;


@Controller
public class FileUIController {

    private static final Logger LOGGER = Logger.getLogger(FileController.class.getName());

    @GetMapping("/let-upload")
    public String doUpload() {
        return "upload-file";
    }

    @GetMapping("/list-files")
    public String listFiles(Model model) {
        try {
            File folder = new File(STORAGE_DIRECTORY);
            File[] files = folder.listFiles();
            List<String> fileNames = new ArrayList<>();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileNames.add(file.getName());
                    }
                }
            }

            model.addAttribute("files", fileNames);
            return "list-files";

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error listing files", e);
            model.addAttribute("error", "Could not list files.");
            return "list-files";
        }
    }
}
