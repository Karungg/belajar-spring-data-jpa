package belajarspringdatajpa.belajar_spring_data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import belajarspringdatajpa.belajar_spring_data_jpa.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
