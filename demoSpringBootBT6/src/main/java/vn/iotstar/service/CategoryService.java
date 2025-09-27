package vn.iotstar.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.iotstar.entity.Category;

public interface CategoryService {
	Page<Category> findAll(String keyword, Pageable pageable);

	Category getById(Integer id);

	void save(Category category);

	void delete(Integer id);
}
