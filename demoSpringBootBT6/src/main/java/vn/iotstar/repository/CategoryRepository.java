package vn.iotstar.repository; // Đảm bảo đúng package

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.model.Category; // <-- QUAN TRỌNG: Import đúng lớp Category

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> { // <-- SỬA Ở ĐÂY

	// Phương thức này bây giờ sẽ trả về đúng Page<Category>
	Page<Category> findByCategoryNameContaining(String name, Pageable pageable);
}