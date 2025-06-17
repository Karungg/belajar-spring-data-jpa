package belajarspringdatajpa.belajar_spring_data_jpa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import belajarspringdatajpa.belajar_spring_data_jpa.entity.Category;
import belajarspringdatajpa.belajar_spring_data_jpa.entity.Product;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCreateProduct() {
        Category gadgetCategory = categoryRepository.findById(1L).orElse(null);
        assertNotNull(gadgetCategory);
        assertEquals("GADGET MURAH", gadgetCategory.getName());

        {
            Product product = new Product();
            product.setCategory(gadgetCategory);
            product.setName("Samsung Galaxy S25 Ultra");
            product.setPrice(25_000_000L);
            productRepository.save(product);
        }

        {
            Product product = new Product();
            product.setCategory(gadgetCategory);
            product.setName("Iphone 16 Pro Max");
            product.setPrice(30_000_000L);
            productRepository.save(product);
        }

        List<Product> products = productRepository.findAllByCategory_Name("GADGET MURAH");

        assertEquals(2, products.size());
        assertEquals("Samsung Galaxy S25 Ultra", products.get(0).getName());
        assertEquals("Iphone 16 Pro Max", products.get(1).getName());
    }

}
