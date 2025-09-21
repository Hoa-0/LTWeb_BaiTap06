package vn.iotstar.service;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import org.springframework.core.io.Resource;

public interface IStorageService {
	void init();

	String store(MultipartFile file);

	Path load(String filename);

	Resource loadAsResource(String filename);

	void delete(String filename) throws Exception;
}