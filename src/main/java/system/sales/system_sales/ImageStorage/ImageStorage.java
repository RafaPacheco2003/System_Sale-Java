package system.sales.system_sales.ImageStorage;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorage {
    String saveImage(MultipartFile image);
}
