package com.sqisoft.testproject.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqisoft.testproject.domain.ContentEntity;
import com.sqisoft.testproject.domain.MuseumEntity;

@Repository
public interface ApiContentRepo extends JpaRepository<ContentEntity, Integer>
{
}
