package io.samtech.configuration.configurer.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.samtech.exception.ImageUploadException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class CloudService implements ICloudService {
    private final Cloudinary cloudinary;
    @Override
    public String upload(MultipartFile image) {
        try{
            Map<?, ?> response =
                    cloudinary.uploader().upload(image.getBytes(),
                            ObjectUtils.emptyMap());
            return response.get("url").toString();
        } catch(IOException e) {
            throw new ImageUploadException(e.getMessage());
        }
    }
}
