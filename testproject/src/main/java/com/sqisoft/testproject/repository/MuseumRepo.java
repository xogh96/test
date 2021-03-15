package com.sqisoft.testproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqisoft.testproject.domain.MuseumEntity;

@Repository
public interface MuseumRepo extends JpaRepository<MuseumEntity, Integer>
{
	MuseumEntity findByDeviceEntityDeviceSeq(Integer DeviceSeq);
}
