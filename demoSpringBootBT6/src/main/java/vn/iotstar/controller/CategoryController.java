package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.Category;
import vn.iotstar.service.CategoryService;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "") String keyword) {
        var pageable = PageRequest.of(page, 5);
        var categories = categoryService.findAll(keyword, pageable);

        model.addAttribute("listCategories", categories.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categories.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "admin/categories/list";
    }

    @GetMapping("/new")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        return "admin/categories/addOrEdit";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        return "admin/categories/addOrEdit";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category") Category category,
                               @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String uploadDir = new File("src/main/resources/static/images").getAbsolutePath();

            File saveFile = new File(uploadDir, fileName);
            file.transferTo(saveFile);

            category.setIcon(fileName);
        }

        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        categoryService.delete(id);
        return "redirect:/admin/categories";
    }
}
