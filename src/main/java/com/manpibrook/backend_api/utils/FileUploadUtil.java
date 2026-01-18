package com.manpibrook.backend_api.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

public class FileUploadUtil {

    /**
     * @param uploadDir Thư mục gốc (vd: src/main/webapp/images)
     * @param subFolder Thư mục con muốn lưu (vd: students, products)
     * @param file Đối tượng file từ request
     * @return Tên file mới đã lưu (để lưu vào DB)
     * @throws IOException Ném lỗi kèm thông báo chi tiết nếu thất bại
     */
    public static String saveFile(String uploadDir, String subFolder, MultipartFile file) throws IOException {
        // 1. Kiểm tra file rỗng
        if (file == null || file.isEmpty()) {
            throw new IOException("Thất bại: File không có dữ liệu hoặc chưa được chọn.");
        }
        // 2. Kiểm tra định dạng (Chỉ cho phép ảnh)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("Thất bại: Tệp tin không phải là hình ảnh hợp lệ.");
        }
        
        try {
            // 3. Thiết lập đường dẫn đầy đủ: root/subFolder
            Path staticPath = Paths.get(uploadDir, subFolder);
            
            // 4. Kiểm tra và tạo thư mục nếu chưa có
            if (!Files.exists(staticPath)) {
                Files.createDirectories(staticPath);
            }

            // 5. Tạo tên file duy nhất (UUID) để không bị ghi đè khi trùng tên gốc
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + extension;

            // 6. Thực hiện lưu file
            Path filePath = staticPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Trả về đường dẫn tương đối để lưu vào DB (vd: students/abc-123.jpg)
            return subFolder + "/" + uniqueFileName;

        } catch (IOException e) {
            throw new IOException("Thất bại hệ thống: Không thể ghi file. Chi tiết: " + e.getMessage());
        }
    }
}