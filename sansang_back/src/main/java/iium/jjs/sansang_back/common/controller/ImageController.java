package iium.jjs.sansang_back.common.controller;

import io.github.classgraph.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

  @Value("${files.file-url}")
  private String filePath;

//  @GetMapping("/member/profile/{imageName:.+}")
//  public ResponseEntity<Resource> serveImage(@PathVariable String imageName) {
//  }
}
