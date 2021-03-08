package com.sqisoft.testproject.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqisoft.testproject.apis.model.ApiDeviceDto;
import com.sqisoft.testproject.apis.model.ApiMuseumDto;
import com.sqisoft.testproject.apis.service.ApiDeviceService;
import com.sqisoft.testproject.apis.service.ApiMuseumService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/museum")
@RestController
public class ApiMuseumController
{
	@Autowired
	private ApiMuseumService apiMuseumService;

	@GetMapping("")
	public List<ApiMuseumDto.info> findAll()
	{
		log.debug("museum find all");
		return apiMuseumService.selectAll();
	}

	@GetMapping("/{museumSeq}")
	public ApiMuseumDto.info findOne(ApiMuseumDto.find museumDto)
	{
		return apiMuseumService.selectOne(museumDto);
	}

	@GetMapping({ "/edit/{museumSeq}", "/add" })
	public ApiMuseumDto.info add(ApiMuseumDto.save museumDto)
	{
		log.debug("add 옴");
		return apiMuseumService.insertOne(museumDto);
	}

	@GetMapping("/remove/{museumSeq}")
	public void del(ApiMuseumDto.delete museumDto)
	{
		apiMuseumService.deleteOne(museumDto);
	}
}
