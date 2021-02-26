package com.sqisoft.testproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqisoft.testproject.domain.ContentEntity;
import com.sqisoft.testproject.domain.DeviceEntity;
import com.sqisoft.testproject.domain.MuseumEntity;
import com.sqisoft.testproject.domain.UserEntity;

@Repository
public interface ContentRepo extends JpaRepository<ContentEntity, Integer>
{

	void deleteAllByCategoryEntityCategorySeq(Integer categorySeq);
}
