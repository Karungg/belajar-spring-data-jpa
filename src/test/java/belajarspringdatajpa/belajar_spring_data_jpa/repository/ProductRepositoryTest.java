package belajarspringdatajpa.belajar_spring_data_jpa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import belajarspringdatajpa.belajar_spring_data_jpa.entity.Category;
import belajarspringdatajpa.belajar_spring_data_jpa.entity.Product;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setName("GADGET MURAH");
        categoryRepository.save(category);

        productRepository.deleteAll();
    }

    private Category getCategory() {
        return categoryRepository.findFirstByNameEquals("GADGET MURAH").orElse(null);
    }

    private Product storeProduct(String name, Long price, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Test
    void findAllByCategory_name() {
        Category gadgetCategory = getCategory();
        assertNotNull(gadgetCategory);
        assertEquals("GADGET MURAH", gadgetCategory.getName());

        {
            storeProduct("Samsung Galaxy S25 Ultra", 2500000L, gadgetCategory);
            storeProduct("Iphone 16 Pro Max", 3000000L, gadgetCategory);
        }

        List<Product> products = productRepository.findAllByCategory_Name("GADGET MURAH");
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("Samsung Galaxy S25 Ultra", products.get(0).getName());
        assertEquals("Iphone 16 Pro Max", products.get(1).getName());
    }

    @Test
    void findAllByCategory_name_sort() {
        Category gadgetCategory = getCategory();
        assertNotNull(gadgetCategory);
        assertEquals("GADGET MURAH", gadgetCategory.getName());

        {
            storeProduct("ROG Phone 8", 2500000L, gadgetCategory);
            storeProduct("Xiaomi 15 Ultra", 3000000L, gadgetCategory);
        }

        Sort sort = Sort.by(Sort.Order.desc("id"));
        List<Product> products = productRepository.findAllByCategory_Name("GADGET MURAH", sort);
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("Xiaomi 15 Ultra", products.get(0).getName());
        assertEquals("ROG Phone 8", products.get(1).getName());
    }

}
