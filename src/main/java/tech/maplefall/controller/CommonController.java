package tech.maplefall.controller;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RequestMapping("/common")
@RestController
public class CommonController {

    @Value("${upload.path}")
    private String uploadPath;

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
