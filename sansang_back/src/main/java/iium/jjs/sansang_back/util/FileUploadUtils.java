package iium.jjs.sansang_back.util;

import iium.jjs.sansang_back.exception.FileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileUploadUtils {

    public static String saveFile(String uploadDir, MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String replaceFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(multipartFile.getResource().getFilename());

        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(replaceFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ex){
            throw new IOException("Could not save file: " + multipartFile.getOriginalFilename(), ex);
        }

        return replaceFileName;
    }

    public static boolean deleteFile(String uploadDir, String fileName) {

        boolean result = false;
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            result = true;
        }

        try {
            Path filePath = uploadPath.resolve(fileName);
            Files.delete(filePath);
            result = true;
        }catch (IOException ex){
            log.info("Could not delete file: {} " , fileName);
            throw new FileUploadException("파일 삭제 중 오류가 발생했습니다");
        }

        return result;
    }

    public static List<String> saveMultiFiles(String uploadDir, List<MultipartFile> multipartFiles) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        List<String> savedFile = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {

            String originalFileName = multipartFile.getOriginalFilename();
            String replaceFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(originalFileName);

            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(replaceFileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                savedFile.add(replaceFileName);
            } catch (IOException ex) {
                throw new IOException("Could not save file: " + originalFileName, ex);
            }
        }
        return savedFile;
    }
}