package belajarspringdatajpa.belajar_spring_data_jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import belajarspringdatajpa.belajar_spring_data_jpa.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory_Name(String name);

}
