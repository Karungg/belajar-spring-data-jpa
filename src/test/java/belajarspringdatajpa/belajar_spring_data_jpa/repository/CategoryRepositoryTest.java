package belajarspringdatajpa.belajar_spring_data_jpa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import belajarspringdatajpa.belajar_spring_data_jpa.entity.Category;

@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCreate() {
        Category category = new Category();
        category.setName("GADGET");

        categoryRepository.save(category);

        assertNotNull(category);
    }

    @Test
    void testUpdate() {
        Category category = categoryRepository.findById(1L).orElse(null);

        category.setName("GADGET MURAH");
        categoryRepository.save(category);

        category = categoryRepository.findById(1L).orElse(null);
        assertNotNull(category);
        assertEquals("GADGET MURAH", category.getName());

    }

    @Test
    void testFindAllByNameEquals() {
        Category category = new Category();
        category.setName("GADGET MURAH");
        categoryRepository.save(category);

        Category categoryData = categoryRepository.findFirstByNameEquals("GADGET MURAH").orElse(null);
        assertNotNull(categoryData);
        assertEquals("GADGET MURAH", categoryData.getName());

        List<Category> categories = categoryRepository.findAllByNameLike("%GADGET%");
        assertEquals(1, categories.size());
        assertEquals("GADGET MURAH", categories.get(0).getName());
    }

}
