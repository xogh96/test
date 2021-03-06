package com.sqisoft.testproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqisoft.testproject.domain.CategoryEntity;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer>
{

	List<CategoryEntity> findByMuseumEntityMuseumSeq(Integer museumSeq);
}
