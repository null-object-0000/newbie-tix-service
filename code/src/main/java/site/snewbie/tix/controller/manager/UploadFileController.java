package site.snewbie.tix.controller.manager;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload/file")
public class UploadFileController {

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("source") MultipartFile source) throws IOException {
        HttpRequest httpRequest = HttpUtil.createPost("https://freeimage.host/api/1/upload?key=6d207e02198a847aa98d0a2a901485a5")
                .form("action", "upload")
                .form("source", source.getBytes(), source.getOriginalFilename())
                .form("format", "json");

        try (HttpResponse httpResponse = httpRequest.execute()) {
            if (httpResponse.isOk()) {
                return ResponseEntity.ok(httpResponse.body());
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }
}
