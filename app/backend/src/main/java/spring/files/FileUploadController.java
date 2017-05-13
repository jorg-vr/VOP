package spring.files;

import csv.CSVtoVehicleParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pdf.GreenCard;
import spring.files.storage.StorageFileNotFoundException;
import spring.files.storage.StorageService;
import spring.model.RESTVehicle;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public HttpEntity<byte[]> serveFile(@PathVariable String filename) {

        byte[] documentBody = new GreenCard(null).getAsByteArray();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + "groene_kaart.pdf");
        header.setContentLength(documentBody.length);

        return new HttpEntity<>(documentBody, header);
    }

    @PostMapping("/${path.vehicles}/${path.import}")
    public Collection<RESTVehicle> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            return CSVtoVehicleParser.parse(file.getInputStream())
                    .stream()
                    .map(RESTVehicle::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
