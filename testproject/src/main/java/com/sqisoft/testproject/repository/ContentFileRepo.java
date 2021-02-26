package com.sqisoft.testproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqisoft.testproject.domain.ContentFileEntity;
import com.sqisoft.testproject.domain.UserEntity;

@Repository
public interface ContentFileRepo extends JpaRepository<ContentFileEntity, Integer>
{
}
