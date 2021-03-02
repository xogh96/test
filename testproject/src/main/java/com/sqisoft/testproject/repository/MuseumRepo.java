package com.sqisoft.testproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqisoft.testproject.domain.DeviceEntity;
import com.sqisoft.testproject.domain.MuseumEntity;

@Repository
public interface MuseumRepo extends JpaRepository<MuseumEntity, Integer>
{

	List<MuseumEntity> findByDeviceEntityDeviceSeq(Integer DeviceSeq);
}
