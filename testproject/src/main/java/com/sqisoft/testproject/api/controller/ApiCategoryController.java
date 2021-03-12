package com.sqisoft.testproject.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqisoft.testproject.api.model.ApiCategoryDto;
import com.sqisoft.testproject.api.service.ApiCategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag( name="category" , description = "카테고리 관련 api입니다")
@Slf4j
@RequestMapping("/api/category")
@RestController
public class ApiCategoryController
{
	@Autowired
	private ApiCategoryService apiCategoryService;

	@GetMapping("")
	public ResponseEntity<?> findAll()
	{
		List<ApiCategoryDto.categoryInfo> infolist = apiCategoryService.selectAll();
		if (infolist.size() == 0)
		{
			return new ResponseEntity<String>("카테고리가 없습니다 등록먼저 해주세요", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<ApiCategoryDto.categoryInfo>>(infolist, HttpStatus.OK);
	}

	@GetMapping("/{categorySeq}")
	public ResponseEntity<?> findOne(ApiCategoryDto.categoryFind categoryDto)
	{
		log.debug("selectone 도착");
		ApiCategoryDto.categoryInfo info = null;
		try
		{
			info = apiCategoryService.selectOne(categoryDto);
		} catch (NullPointerException ne)
		{
			return new ResponseEntity<String>("존재하지 않는 카테고리입니다 카테고리 seq를 다시 확인해주세요", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApiCategoryDto.categoryInfo>(info, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<?> add(ApiCategoryDto.categorySave categoryDto)
	{
		ApiCategoryDto.categoryInfo info = null;
		try
		{
			info = apiCategoryService.insertOne(categoryDto);
		} catch (Exception e)
		{
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApiCategoryDto.categoryInfo>(info, HttpStatus.OK);
	}

	@PostMapping("/edit/{categorySeq}")
	public ApiCategoryDto.categoryInfo update(ApiCategoryDto.categoryUpdate categoryDto)
	{
		return apiCategoryService.updateOne(categoryDto);
	}

	@PostMapping("/remove/{categorySeq}")
	public ResponseEntity<String> del(ApiCategoryDto.categoryDelete categoryDto)
	{
		try
		{
			apiCategoryService.deleteOne(categoryDto);
		} catch (EmptyResultDataAccessException ee)
		{
			return new ResponseEntity<String>("존재하지 않는 카테고리입니다 카테고리 seq를 다시 확인해주세요", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}
}
