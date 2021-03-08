package com.sqisoft.testproject.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqisoft.testproject.apis.model.ApiDeviceDto;
import com.sqisoft.testproject.apis.service.ApiDeviceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/device")
@RestController
public class ApiDeviceController
{
	@Autowired
	private ApiDeviceService apiDeviceService;

	@GetMapping("")
	public List<ApiDeviceDto.info> findAll()
	{
		return apiDeviceService.selectAll();
	}

	@GetMapping("/{deviceSeq}")
	public ApiDeviceDto.info findOne(ApiDeviceDto.find deviceDto)
	{
		return apiDeviceService.selectOne(deviceDto);
	}

	@GetMapping({ "/edit/{deviceSeq}", "/add" })
	public ApiDeviceDto.info add(ApiDeviceDto.save deviceDto)
	{
		return apiDeviceService.insertOne(deviceDto);
	}

	@GetMapping("/remove/{deviceSeq}")
	public void del(ApiDeviceDto.delete deviceDto)
	{
		apiDeviceService.deleteOne(deviceDto);
	}
}
