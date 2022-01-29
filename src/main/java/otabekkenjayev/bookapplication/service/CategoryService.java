package otabekkenjayev.bookapplication.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otabekkenjayev.bookapplication.dto.ApiResponse;
import otabekkenjayev.bookapplication.dto.CategoryDTO;
import otabekkenjayev.bookapplication.model.Category;
import otabekkenjayev.bookapplication.repository.CategoryRepository;
import otabekkenjayev.bookapplication.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse save(CategoryDTO categoryDTO) {
        boolean existsByName = categoryRepository.existsByName(categoryDTO.getName());

        if (existsByName) {
            return new ApiResponse("This Category already exist!!", false);
        }

        Category category = new Category();
        category.setName(categoryDTO.getName());

        if (categoryDTO.getParentCategoryId() != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDTO.getParentCategoryId());
            if (!optionalCategory.isPresent())
                return new ApiResponse("Category Not Found!", false);
            category.setParentCategoryId(categoryDTO.getParentCategoryId());
        }

        categoryRepository.save(category);

        return new ApiResponse("Saved", true, category.getName());
    }

    public ApiResponse delete(Integer id) {
        categoryRepository.deleteById(id);
        return new ApiResponse("Deleted!", true);
    }

    public ApiResponse edit(Integer id, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (!optionalCategory.isPresent())
            return new ApiResponse("Category Not Found", false);

        Category category = optionalCategory.get();
        category.setName(categoryDTO.getName());
        category.setActive(categoryDTO.isActive());

        if (categoryDTO.getParentCategoryId() != null) {
            Optional<Category> optional = categoryRepository.findById(categoryDTO.getParentCategoryId());
            if (!optional.isPresent())
                return new ApiResponse("Category Not Found", false);

            category.setParentCategoryId(categoryDTO.getParentCategoryId());
        }

        Category save = categoryRepository.save(category);

        return new ApiResponse("Edited!", true, save);


    }

    public ApiResponse getAll() {
        List<Category> all = categoryRepository.findAll();
        return new ApiResponse("This", true, all);
    }

    public ApiResponse getOneById(Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);
        return new ApiResponse("This", true, byId);
    }

    public ApiResponse deleteAll() {
        categoryRepository.deleteAll();
        return new ApiResponse("All Category Deleted!", true);
    }

    public ApiResponse getChildCategories(Integer id) {
        List<Category> childCategories = categoryRepository.findAllByParentCategoryId(id);
        return new ApiResponse("This", true, childCategories);
    }
}

