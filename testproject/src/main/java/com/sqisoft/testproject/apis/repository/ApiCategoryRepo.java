package com.sqisoft.testproject.apis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqisoft.testproject.domain.CategoryEntity;
import com.sqisoft.testproject.domain.DeviceEntity;

@Repository
public interface ApiCategoryRepo extends JpaRepository<CategoryEntity, Integer>
{
}
