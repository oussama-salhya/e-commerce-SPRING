package com.ouss.ecom.config;



import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUpload {
    String uploadFile(String base64String) throws IOException;
}