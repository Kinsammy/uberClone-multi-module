package io.samtech.configuration.configurer.cloudinary;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudService {
    String upload(MultipartFile image);
}
