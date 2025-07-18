package tech.maplefall.controller.admin;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.maplefall.util.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Slf4j
@RequestMapping("/common")
@RestController
public class CommonController {

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile multipartFile) {
        String name = UUID.randomUUID().toString() + "-" +multipartFile.getOriginalFilename();
        try {
            FileUtil.writeBytes(multipartFile.getBytes(), uploadPath + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(name);

    }

    @GetMapping("/download")
    private void downloadFile(String name, HttpServletResponse response) {
        byte[] bytes = FileUtil.readBytes(uploadPath + name);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            os.write(bytes);
            os.flush();
            os.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
