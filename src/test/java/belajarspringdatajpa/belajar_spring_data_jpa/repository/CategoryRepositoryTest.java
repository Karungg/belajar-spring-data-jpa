package belajarspringdatajpa.belajar_spring_data_jpa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import belajarspringdatajpa.belajar_spring_data_jpa.entity.Category;

@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();

        Category category = createCategory();
        assertNotNull(category);
    }

    private Category createCategory() {
        Category category = new Category();
        category.setName("GADGET MURAH");
        return categoryRepository.save(category);
    }

    private Category getCategory() {
        return categoryRepository.findById(1L).orElse(null);
    }

    @Test
    void testCreate() {
        Category category = new Category();
        category.setName("GADGET MURAH");
        categoryRepository.save(category);

        assertNotNull(category);
    }

    @Test
    void testFindAllByNameEquals() {
        Category categoryData = categoryRepository.findFirstByNameEquals("GADGET MURAH").orElse(null);
        assertNotNull(categoryData);
        assertEquals("GADGET MURAH", categoryData.getName());

        List<Category> categories = categoryRepository.findAllByNameLike("%GADGET%");
        assertEquals(1, categories.size());
        assertEquals("GADGET MURAH", categories.get(0).getName());
    }

    @Test
    void testAudit() {
        Category category = getCategory();
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getUpdatedAt());
    }

}
