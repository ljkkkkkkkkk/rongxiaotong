package com.lk77.server.controller;

import com.lk77.server.protocal.HttpResult;
import com.lk77.server.constant.Constant;
import com.lk77.server.exception.FileFormatException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/file")
public class FilesUploadController {
    @Value("${application.upload-path}")
    private String fileDirectory;

    @ApiOperation(value = "头像上传")
    @PostMapping("/upload/{type}")
    public HttpResult<String> upload(@PathVariable("type") String type,
                                     @RequestParam("file") MultipartFile file) throws Exception {
        validateAndUploadFile(type, file);
        return new HttpResult<>(true, Constant.OK, "上传成功", generateFileName(file));
    }

    @ApiOperation(value = "资料上传")
    @PostMapping("/uploadInfo/{type}")
    public HttpResult<String> uploadInfo(@PathVariable("type") String type,
                                         @RequestParam("file") MultipartFile file) throws Exception {
        validateAndUploadFile(type, file);
        return new HttpResult<>(true, Constant.OK, "上传成功", generateFileName(file));
    }

    private void validateAndUploadFile(String type, MultipartFile file) throws Exception {
        validateFile(file);
        String newFileName = generateFileName(file);
        String targetFileLocation = fileDirectory + File.separator + type;
        File targetDirectory = new File(targetFileLocation);
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }
        String targetFileName = targetFileLocation + File.separator + newFileName;
        File targetFile = new File(targetFileName);
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        file.transferTo(targetFile);
    }

    private void validateFile(MultipartFile file) throws FileFormatException {
        String originalFilename = file.getOriginalFilename();
        String tail = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        if (!isAllowedExtension(tail)) {
            throw new FileFormatException("请传入正确格式文件");
        }
    }

    private boolean isAllowedExtension(String extension) {
        return extension != null && (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".gif") ||
                extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".webp") || extension.equalsIgnoreCase(".mp4"));
    }

    private String generateFileName(MultipartFile file) {
        String header = UUID.randomUUID().toString().replaceAll("-", "");
        String originalFilename = file.getOriginalFilename();
        String tail = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        return header + tail;
    }
}
