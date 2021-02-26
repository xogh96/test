package com.sqisoft.testproject.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqisoft.testproject.config.SqiException;
import com.sqisoft.testproject.domain.DeviceEntity;
import com.sqisoft.testproject.repository.DeviceRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeviceService
{
	@Autowired
	private DeviceRepo deviceRepository;

	@Transactional
	public List<DeviceEntity> selectAll()
	{
		return deviceRepository.findAll();
	}

	@Transactional
	public Optional<DeviceEntity> selectOne(Integer deviceSeq)
	{
		return deviceRepository.findById(deviceSeq);
	}

	@Transactional
	public boolean modifyOne(Integer deviceSeq, DeviceEntity deviceEntity) throws IOException
	{
		
		DeviceEntity saveddeviceEntity = deviceRepository.findById(deviceSeq).orElse(null);
		// device 저장
		saveddeviceEntity.setDeviceName(deviceEntity.getDeviceName());
		
		DeviceEntity savedEntity = deviceRepository.save(saveddeviceEntity);
		if (savedEntity == null)
		{
			return false;
		} 
		else return true;
	}

	@Transactional
	public boolean insertOne(DeviceEntity deviceEntity) throws IOException
	{
		if(!deviceRepository.existsBydeviceCode(deviceEntity.getDeviceCode())) {
			DeviceEntity savedEntity = deviceRepository.save(deviceEntity);
			if (savedEntity != null && savedEntity.getDeviceSeq() > 0)
			{
				return true;
			}
			return false;
		}
		else {
			throw new SqiException("중복된 코드입니다 다시 입력해주세요");
		}
	}

	@Transactional
	public void deleteOne(Integer deviceSeq)
	{
		deviceRepository.deleteById(deviceSeq);
	}
	
	
	

}
