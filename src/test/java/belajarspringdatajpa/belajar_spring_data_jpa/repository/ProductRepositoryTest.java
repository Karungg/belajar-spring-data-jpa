package belajarspringdatajpa.belajar_spring_data_jpa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        Category gadgetCategory = getCategory();
        assertNotNull(gadgetCategory);
        assertEquals("GADGET MURAH", gadgetCategory.getName());

        {
            storeProduct("ROG Phone 8", 2500000L, gadgetCategory);
            storeProduct("Xiaomi 15 Ultra", 3000000L, gadgetCategory);
        }
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
        List<Product> products = productRepository.findAllByCategory_Name("GADGET MURAH");
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("ROG Phone 8", products.get(0).getName());
        assertEquals("Xiaomi 15 Ultra", products.get(1).getName());
    }

    @Test
    void findAllByCategory_name_sort() {
        Sort sort = Sort.by(Sort.Order.desc("id"));
        List<Product> products = productRepository.findAllByCategory_Name("GADGET MURAH", sort);
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("Xiaomi 15 Ultra", products.get(0).getName());
        assertEquals("ROG Phone 8", products.get(1).getName());
    }

    @Test
    void testPageResult() {
        // Page 0
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.asc("id")));
        Page<Product> products = productRepository.findAllByCategory_Name("GADGET MURAH", pageable);

        assertEquals(1, products.getContent().size());
        assertEquals(0, products.getNumber());
        assertEquals(2, products.getTotalElements());
        assertEquals(2, products.getTotalPages());
        assertEquals("ROG Phone 8", products.getContent().get(0).getName());

        // Page 1
        pageable = PageRequest.of(1, 1, Sort.by(Sort.Order.asc("id")));
        products = productRepository.findAllByCategory_Name("GADGET MURAH", pageable);

        assertEquals(1, products.getContent().size());
        assertEquals(1, products.getNumber());
        assertEquals(2, products.getTotalElements());
        assertEquals(2, products.getTotalPages());
        assertEquals("Xiaomi 15 Ultra", products.getContent().get(0).getName());
    }

    @Test
    void testCount() {
        Long count = productRepository.count();
        assertEquals(2L, count);

        count = productRepository.countByCategory_Name("GADGET MURAH");
        assertEquals(2L, count);
    }
}
