package com.sqisoft.testproject.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqisoft.testproject.apis.model.ApiCategoryDto;
import com.sqisoft.testproject.apis.model.ApiDeviceDto;
import com.sqisoft.testproject.apis.service.ApiCategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/category")
@RestController
public class ApiCategoryController
{
	@Autowired
	private ApiCategoryService apiCategoryService;

	@GetMapping("")
	public List<ApiCategoryDto.info> findAll()
	{
		return apiCategoryService.selectAll();
	}

	@GetMapping("/{categorySeq}")
	public ApiCategoryDto.info findOne(ApiCategoryDto.find categoryDto)
	{
		log.debug("selectone 도착");
		return apiCategoryService.selectOne(categoryDto);
	}

	@GetMapping({ "/edit/{categorySeq}", "/add" })
	public ApiCategoryDto.info add(ApiCategoryDto.save categoryDto)
	{
		return apiCategoryService.insertOne(categoryDto);
	}

	@GetMapping("/remove/{categorySeq}")
	public void del(ApiCategoryDto.delete categoryDto)
	{
		apiCategoryService.deleteOne(categoryDto);
	}
}
