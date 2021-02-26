package com.sqisoft.testproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sqisoft.testproject.domain.DeviceEntity;

@Repository
public interface DeviceRepo extends JpaRepository<DeviceEntity, Integer>
{

	boolean existsBydeviceCode(String deviceCode);
}
