package com.education.amenity.management;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.UUID;

@Service
public class StudentService {
    private static final String UPLOAD_DIR = "uploads/";

    public String saveDocument(MultipartFile file, String studentName) throws Exception {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);

        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        return path.toString();
    }

    public boolean deleteDocument(String fileName) throws Exception {
        Path path = Paths.get(UPLOAD_DIR + fileName);

        if (Files.exists(path)) {
            Files.delete(path);
            return true;
        }
        return false;
    }


    public boolean copyDocument(String fileName, String newFileName) throws Exception {
        Path sourcePath = Paths.get(UPLOAD_DIR + fileName);
        Path destinationPath = Paths.get(UPLOAD_DIR + newFileName);

        if (Files.exists(sourcePath)) {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        }
        return false;
    }

}
