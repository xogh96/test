package com.sqisoft.testproject.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqisoft.testproject.apis.model.ApiContentDto;
import com.sqisoft.testproject.apis.model.ApiDeviceDto;
import com.sqisoft.testproject.apis.model.ApiMuseumDto;
import com.sqisoft.testproject.apis.service.ApiContentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/content")
@RestController
public class ApiContentController
{
	@Autowired
	private ApiContentService apiContentService;

	@GetMapping("")
	public List<ApiContentDto.info> findAll()
	{
		return apiContentService.selectAll();
	}
	
	@GetMapping("/{contentSeq}")
	public ApiContentDto.info findOne(ApiContentDto.find contentDto){
		return apiContentService.selectOne(contentDto);
	}
	
	@GetMapping({ "/edit/{contentSeq}", "/add" })
	public ApiContentDto.info add(ApiContentDto.save contentDto)
	{
		return apiContentService.insertOne(contentDto);
	}

	
	@GetMapping("/remove/{contentSeq}")
	public void del(ApiContentDto.delete deviceDto)
	{
		apiContentService.deleteOne(deviceDto);
	}
}
