package bsn.backend.FILESERVICES;

import bsn.backend.ENTITIES.Book;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {
    @Value("${spring.application.file.upload.photos-output-path}")
    private String fileUploadPath ;
    public String saveFile(@NotNull MultipartFile sourceFile, @NotNull Integer userId) {

final String fileUploadSubPath = "users" + File.separator + userId;
return uploadFile(sourceFile,fileUploadSubPath);
    }

    private String uploadFile(@NotNull MultipartFile sourceFile, @NotNull String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + File.separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);
        if(!targetFolder.exists()){
            boolean targetCreated = targetFolder.mkdirs();
            if(!targetCreated){
    log.warn("Failed to create the target folder");
    return null;
            }
        }
        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath = finalUploadPath + File.separator + System.currentTimeMillis() + "." + fileExtension;
        Path targetPath = Path.of(targetFilePath);
        try{
            Files.write(targetPath,sourceFile.getBytes());
            log.info("File Saved Successfully");
            return targetFilePath;

        }catch (Exception e){
log.error("File Was not saved");
        }
return "";
    }

    private String getFileExtension(String originalFilename) {
        if(originalFilename.isEmpty() || originalFilename == null){
            return "";
        }
        int lastIndex = originalFilename.lastIndexOf(".");
        if(lastIndex == -1){
            return "";
        }

        return originalFilename.substring(lastIndex+1).toLowerCase();
    }
}
