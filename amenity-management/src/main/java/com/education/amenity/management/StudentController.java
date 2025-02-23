package com.education.amenity.management;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
public class StudentController {
    private final StudentService documentService;
    public StudentController(StudentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                    @RequestParam("studentName") String studentName) {
        try {
            String filePath = documentService.saveDocument(file, studentName);
            return ResponseEntity.ok("Thanks for submitting your student information " + studentName);

        } catch (Exception e){
            return ResponseEntity.status(500).body("Failed to submit proper file " + studentName);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDocument(@RequestParam("fileName") String fileName) {
        try {
            boolean isDeleted = documentService.deleteDocument(fileName);
            if (isDeleted) {
                return ResponseEntity.ok("File deleted successfully: " + fileName);
            } else {
                return ResponseEntity.status(404).body("File not found: " + fileName);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete file: " + fileName);
        }
    }

    @PostMapping("/copy")
    public ResponseEntity<?> copyDocument(@RequestParam("sourceFileName") String sourceFileName,
                                          @RequestParam("destinationFileName") String destinationFileName) {
        try {
            boolean isCopied = documentService.copyDocument(sourceFileName, destinationFileName);
            if (isCopied) {
                return ResponseEntity.ok("File copied successfully from " + sourceFileName + " to " + destinationFileName);
            } else {
                return ResponseEntity.status(404).body("Source file not found: " + sourceFileName);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to copy file: " + sourceFileName);
        }
    }



}
