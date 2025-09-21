package vn.iotstar.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.nio.file.*;

@Service
public class FileSystemStorageService implements IStorageService {

	@Value("${storage.location}")
	private String storageLocation;

	private Path rootLocation;

	@Override
	public void init() {
		try {
			this.rootLocation = Paths.get(storageLocation);
			if (Files.notExists(rootLocation)) {
				Files.createDirectories(rootLocation);
			}
		} catch (Exception e) {
			// Xử lý lỗi
		}
	}

	@Override
	public String store(MultipartFile file) {
		if (file.isEmpty()) {
			return null;
		}
		String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		try {
			Path destinationFile = this.rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
			return filename;
		} catch (Exception e) {
			return null;
		}
	}

	// Các phương thức khác có thể implement sau nếu cần
	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			// Xử lý lỗi
			return null;
		} catch (Exception e) {
			// Xử lý lỗi
			return null;
		}
	}

	@Override
	public void delete(String filename) throws Exception {
		Path file = load(filename);
		Files.deleteIfExists(file);
	}
}