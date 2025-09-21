package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.iotstar.model.Category;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.service.IStorageService;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private IStorageService storageService;

	// HIỂN THỊ DANH SÁCH & TÌM KIẾM, PHÂN TRANG
	@GetMapping("")
	public String list(ModelMap model, @RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "3") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Category> resultPage;

		if (StringUtils.hasText(name)) {
			resultPage = categoryRepository.findByCategoryNameContaining(name, pageable);
			model.addAttribute("name", name);
		} else {
			resultPage = categoryRepository.findAll(pageable);
		}

		model.addAttribute("categoryPage", resultPage);
		return "admin/categories/list";
	}

	// HIỂN THỊ FORM THÊM MỚI
	@GetMapping("/add")
	public String add(ModelMap model) {
		model.addAttribute("category", new Category());
		return "admin/categories/addOrEdit";
	}

	// XỬ LÝ LƯU HOẶC CẬP NHẬT
	@PostMapping("/saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @ModelAttribute("category") Category category,
			@RequestParam("iconFile") MultipartFile iconFile) {

		// Xử lý upload file icon
		if (!iconFile.isEmpty()) {
			String iconName = storageService.store(iconFile);
			category.setIcon(iconName);
		}

		categoryRepository.save(category);

		String message = category.getCategoryId() == null ? "Category is added!" : "Category is updated!";
		model.addAttribute("message", message);
		return new ModelAndView("redirect:/admin/categories", model);
	}

	// HIỂN THỊ FORM CHỈNH SỬA
	@GetMapping("/edit/{categoryId}")
	public ModelAndView edit(ModelMap model, @PathVariable("categoryId") Long categoryId) {
		Optional<Category> opt = categoryRepository.findById(categoryId);

		if (opt.isPresent()) {
			model.addAttribute("category", opt.get());
			return new ModelAndView("admin/categories/addOrEdit", model);
		}

		model.addAttribute("message", "Category not found!");
		return new ModelAndView("redirect:/admin/categories", model);
	}

	// XỬ LÝ XÓA
	@GetMapping("/delete/{categoryId}")
	public ModelAndView delete(ModelMap model, @PathVariable("categoryId") Long categoryId) {
		categoryRepository.deleteById(categoryId);
		model.addAttribute("message", "Category is deleted!");
		return new ModelAndView("redirect:/admin/categories", model);
	}

	// API ĐỂ HIỂN THỊ ẢNH
	@GetMapping("/uploads/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}