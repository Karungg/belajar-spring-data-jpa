package belajarspringdatajpa.belajar_spring_data_jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import belajarspringdatajpa.belajar_spring_data_jpa.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory_Name(String name);

    List<Product> findAllByCategory_Name(String name, Sort sort);

    Page<Product> findAllByCategory_Name(String name, Pageable pageable);

    Long countByCategory_Name(String name);

    boolean existsByName(String name);

    @Transactional
    int deleteByName(String name);

    Page<Product> searchProductUsingName(@Param("name") String name, Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.name like %:name% or p.category.name like %:name%")
    List<Product> searchProduct(@Param("name") String name);

    @Query(value = "SELECT * FROM products WHERE price > 2500000", nativeQuery = true)
    Product searchProductMaxPrice();

}
