package otabekkenjayev.bookapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import otabekkenjayev.bookapplication.dto.ApiResponse;
import otabekkenjayev.bookapplication.dto.CategoryDTO;
import otabekkenjayev.bookapplication.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?>addCategory(@RequestBody CategoryDTO categoryDTO){
        ApiResponse response = categoryService.save(categoryDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public HttpEntity<?> allCategory(){
        ApiResponse all = categoryService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/allByParent/{id}")
    public HttpEntity<?> childCategories(@PathVariable Integer id){
        ApiResponse allChilds = categoryService.getChildCategories(id);
        return ResponseEntity.ok(allChilds);
    }

    @GetMapping("/one/{id}")
    public HttpEntity<?> getOneCategory(@PathVariable Integer id){
        ApiResponse oneById = categoryService.getOneById(id);
        return ResponseEntity.ok(oneById);
    }


    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> deleteCategory(@PathVariable Integer id ){
        ApiResponse delete = categoryService.delete(id);
        return  ResponseEntity.ok(delete);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> editCategory(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO){
        ApiResponse edited = categoryService.edit(id,categoryDTO);
        return ResponseEntity.ok(edited);
    }


}
