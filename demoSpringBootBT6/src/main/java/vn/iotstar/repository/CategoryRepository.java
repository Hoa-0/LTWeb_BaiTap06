package vn.iotstar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Page<Category> findByCategoryNameContainingIgnoreCase(String keyword, Pageable pageable);
}
