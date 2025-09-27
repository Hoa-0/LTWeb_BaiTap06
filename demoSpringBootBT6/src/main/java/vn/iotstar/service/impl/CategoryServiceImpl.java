package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Category;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repo;

	@Override
	public Page<Category> findAll(String keyword, Pageable pageable) {
		if (keyword != null && !keyword.isEmpty()) {
			return repo.findByCategoryNameContainingIgnoreCase(keyword, pageable);
		}
		return repo.findAll(pageable);
	}

	@Override
	public Category getById(Integer id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void save(Category category) {
		repo.save(category);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
