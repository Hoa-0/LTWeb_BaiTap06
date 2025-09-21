package vn.iotstar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Lấy đường dẫn tuyệt đối đến thư mục 'uploads'
		Path uploadDir = Paths.get("./uploads/");
		String uploadPath = uploadDir.toFile().getAbsolutePath();

		// Cấu hình resource handler để phục vụ file từ thư mục uploads
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:/" + uploadPath + "/");
	}
}