package bsn.backend.FILESERVICES;


import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileUtils {
    public static byte[] readFileFromLocation(String bookCover) {
        if(StringUtils.isBlank(bookCover)){
            return null;
        }
        try{
Path file = new File(bookCover).toPath();
return Files.readAllBytes(file);
        }catch(IOException e){
            log.error("The file Does not exist");
        }
        return null;
    }
}
