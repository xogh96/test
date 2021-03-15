package com.sqisoft.testproject.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqisoft.testproject.domain.CategoryEntity;

@Repository
public interface ApiCategoryRepo extends JpaRepository<CategoryEntity, Integer>
{
}
