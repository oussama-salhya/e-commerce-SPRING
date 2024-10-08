package com.ouss.ecom.config;


import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
@Service
@AllArgsConstructor
public class FileUploadImpl implements FileUpload {
    private final Cloudinary cloudinary;
    @Override
    public String uploadFile(String base64String) throws IOException {

        return cloudinary.uploader()
                .upload(base64String, Map.of("public_id", "meublux2.0/" + UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }

}