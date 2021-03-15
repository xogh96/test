package com.sqisoft.testproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqisoft.testproject.domain.ContentEntity;

@Repository
public interface ContentRepo extends JpaRepository<ContentEntity, Integer>
{

	void deleteAllByCategoryEntityCategorySeq(Integer categorySeq);
}
