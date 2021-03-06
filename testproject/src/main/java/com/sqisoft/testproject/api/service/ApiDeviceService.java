package com.sqisoft.testproject.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqisoft.testproject.api.model.ApiDeviceDto;
import com.sqisoft.testproject.api.repository.ApiDeviceRepo;
import com.sqisoft.testproject.domain.DeviceEntity;


@Service
public class ApiDeviceService
{
	@Autowired
	private ApiDeviceRepo apiDeviceRepository;

	@Transactional
	public List<ApiDeviceDto.deviceInfo> selectAll()
	{
		List<ApiDeviceDto.deviceInfo> infoList = new ArrayList<ApiDeviceDto.deviceInfo>();
		List<DeviceEntity> deviceList = apiDeviceRepository.findAll();

		for (int i = 0; i < deviceList.size(); i++)
		{
			ApiDeviceDto.deviceInfo info = new ApiDeviceDto.deviceInfo(deviceList.get(i));
			infoList.add(info);
		}
		return infoList;
	}

	@Transactional
	public ApiDeviceDto.deviceInfo selectOne(ApiDeviceDto.deviceFind deviceDto)
	{
		DeviceEntity deviceEntity = apiDeviceRepository.findById(deviceDto.getDeviceSeq()).orElse(null);
		ApiDeviceDto.deviceInfo info = new ApiDeviceDto.deviceInfo(deviceEntity);
		return info;
	}
}
