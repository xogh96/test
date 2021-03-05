package com.sqisoft.testproject.apis.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqisoft.testproject.apis.model.ApiDeviceDto;
import com.sqisoft.testproject.apis.model.ApiDeviceDto.delete;
import com.sqisoft.testproject.apis.repository.ApiDeviceRepo;
import com.sqisoft.testproject.domain.DeviceEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiDeviceService
{
	@Autowired
	private ApiDeviceRepo apiDeviceRepository;

	@Transactional
	public List<ApiDeviceDto.info> selectAll()
	{
		List<ApiDeviceDto.info> infoList = new ArrayList<ApiDeviceDto.info>();
		List<DeviceEntity> deviceList = apiDeviceRepository.findAll();

		for (int i = 0; i < deviceList.size(); i++)
		{
			ApiDeviceDto.info info = new ApiDeviceDto.info(deviceList.get(i));
			infoList.add(info);
		}
		return infoList;
	}

	@Transactional
	public ApiDeviceDto.info selectOne(ApiDeviceDto.find deviceDto)
	{
		DeviceEntity deviceEntity = apiDeviceRepository.findById(deviceDto.getDeviceSeq()).orElse(null);
		ApiDeviceDto.info info = new ApiDeviceDto.info(deviceEntity);
		return info;
	}

	@Transactional
	public ApiDeviceDto.info insertOne(ApiDeviceDto.save deviceDto)
	{
		ApiDeviceDto.info info = null;
		DeviceEntity deviceEntity = null;

		if (deviceDto.getDeviceSeq() == null)
		{
			deviceEntity = new DeviceEntity();
		} else
		{
			deviceEntity = apiDeviceRepository.findById(deviceDto.getDeviceSeq()).orElse(null);
		}
		if (deviceDto.getDeviceCode() != null)
		{
			deviceEntity.setDeviceCode(deviceDto.getDeviceCode());
		}
		if (deviceDto.getDeviceName() != null)
		{
			deviceEntity.setDeviceName(deviceDto.getDeviceName());
		}
		DeviceEntity savedDeviceEntity = apiDeviceRepository.save(deviceEntity);
		info = new ApiDeviceDto.info(savedDeviceEntity);
		return info;
	}

	@Transactional
	public void deleteOne(delete deviceDto)
	{
		apiDeviceRepository.deleteById(deviceDto.getDeviceSeq());
	}
}
