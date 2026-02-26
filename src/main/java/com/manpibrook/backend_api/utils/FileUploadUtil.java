package com.manpibrook.backend_api.utils;

import org.springframework.web.multipart.MultipartFile;

import com.manpibrook.backend_api.entity.enums.EUploadType;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUploadUtil {
	public static String saveFile(String uploadDir, EUploadType type, Long targetId, MultipartFile file)
			throws IOException {

		if (file == null || file.isEmpty()) {
			throw new IOException("File không hợp lệ hoặc rỗng");
		}

		String contentType = file.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new IOException("Chỉ cho phép upload file hình ảnh");
		}

		try {
// 1️⃣ Tạo thư mục theo loại
			Path folderPath = Paths.get(uploadDir, type.getFolder());
			if (!Files.exists(folderPath)) {
				Files.createDirectories(folderPath);
			}

// 2️⃣ Lấy extension an toàn
			String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
			int dotIndex = originalFileName.lastIndexOf(".");
			String extension = (dotIndex != -1) ? originalFileName.substring(dotIndex).toLowerCase() : ".jpg"; // fallback

// 3️⃣ Tạo tên file: PREFIX_ID_TIMESTAMP_RANDOM.ext
			String uniqueFileName = String.format("%s_%d_%d_%d%s", type.getPrefix(), targetId,
					System.currentTimeMillis(), (int) (Math.random() * 1000), extension);

// 4️⃣ Lưu file
			Path filePath = folderPath.resolve(uniqueFileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

// 5️⃣ Trả về path tương đối lưu DB
			return type.getFolder() + "/" + uniqueFileName;

		} catch (IOException e) {
			throw new IOException("Lỗi khi lưu file: " + e.getMessage(), e);
		}
	}

	/**
	 * Lưu nhiều file (vd: ảnh variant)
	 */
	public static List<String> saveMultipleFiles(String uploadDir, EUploadType type, Long targetId,
			MultipartFile[] files) throws IOException {

		List<String> paths = new ArrayList<>();

		if (files == null || files.length == 0) {
			return paths;
		}

		for (MultipartFile file : files) {
			if (file != null && !file.isEmpty()) {
				String path = saveFile(uploadDir, type, targetId, file);
				paths.add(path);
			}
		}

		return paths;
	}

	/**
	 * Xóa file cũ
	 */
	public static void deleteOldFile(String uploadDir, String relativePath) {

		if (relativePath == null || relativePath.isEmpty()) {
			return;
		}

		try {
			Path filePath = Paths.get(uploadDir, relativePath);
			Files.deleteIfExists(filePath);
		} catch (IOException e) {
			System.err.println("Không thể xóa file: " + e.getMessage());
		}
	}
}

//import org.springframework.web.multipart.MultipartFile;
//
//import com.manpibrook.backend_api.entity.enums.EUploadType;
//
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.Objects;

//public class FileUploadUtil {
//
//	    /**
//	     * @param uploadDir Thư mục gốc (vd: src/main/resources/static/images)
//	     * @param type Loại upload (PROFILE, LAPTOP, BANNER...)
//	     * @param targetId ID của đối tượng (UserId, LaptopId, BannerId) để định danh tên file
//	     * @param file Đối tượng file từ request
//	     */
//	    public static String saveFile(String uploadDir, EUploadType type, Long targetId, MultipartFile file) throws IOException {
//	        
//	        // 1. Kiểm tra file cơ bản
//	        if (file == null || file.isEmpty()) {
//	            throw new IOException("Thất bại: File không có dữ liệu.");
//	        }
//
//	        // 2. Kiểm tra định dạng ảnh
//	        String contentType = file.getContentType();
//	        if (contentType == null || !contentType.startsWith("image/")) {
//	            throw new IOException("Thất bại: Tệp tin không phải là hình ảnh hợp lệ.");
//	        }
//
//	        try {
//	            // 3. Thiết lập đường dẫn: root/folder_theo_loai (vd: images/profiles)
//	            Path staticPath = Paths.get(uploadDir, type.getFolder());
//	            if (!Files.exists(staticPath)) {
//	                Files.createDirectories(staticPath);
//	            }
//
//	            // 4. Chuẩn hóa tên file: PREFIX_ID_TIMESTAMP.ext
//	            // Ví dụ: PRFL_15_1706085600123.jpg hoặc LTP_102_1706085600456.png
//	            String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
//	            String extension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
//	            String timestamp = String.valueOf(System.currentTimeMillis());
//	            
//	            String uniqueFileName = String.format("%s_%d_%s%s", 
//	                                    type.getPrefix(), targetId, timestamp, extension);
//
//	            // 5. Thực hiện lưu file
//	            Path filePath = staticPath.resolve(uniqueFileName);
//	            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//	            // Trả về đường dẫn để lưu DB (vd: profiles/PRFL_15_123.jpg)
//	            return type.getFolder() + "/" + uniqueFileName;
//
//	        } catch (IOException e) {
//	            throw new IOException("Lỗi hệ thống khi lưu file: " + e.getMessage());
//	        }
//	    }
//
//	    /**
//	     * Hàm hỗ trợ xóa file cũ để tránh rác server
//	     */
//	    public static void deleteOldFile(String uploadDir, String relativePath) {
//	        if (relativePath == null || relativePath.isEmpty()) {
//	            return;
//	        }
//	        try {
//	            // Kết hợp đường dẫn gốc và đường dẫn tương đối để ra vị trí file vật lý
//	            Path filePath = Paths.get(uploadDir, relativePath);
//	            
//	            // Thực hiện xóa nếu file tồn tại
//	            boolean deleted = Files.deleteIfExists(filePath);
//	            
//	            if (deleted) {
//	                System.out.println("Đã xóa file cũ thành công: " + relativePath);
//	            }
//	        } catch (IOException e) {
//	            // Chỉ log lỗi, không nên chặn luồng nghiệp vụ nếu xóa file cũ thất bại
//	            System.err.println("Lỗi khi xóa file cũ: " + e.getMessage());
//	        }
//	    }
//	
//
//}