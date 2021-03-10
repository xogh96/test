package com.sqisoft.testproject.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqisoft.testproject.apis.model.ApiCategoryDto;
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
	public ResponseEntity<?> findAll()
	{
		List<ApiCategoryDto.info> infolist = apiCategoryService.selectAll();
		if (infolist.size() == 0)
		{
			return new ResponseEntity<String>("카테고리가 없습니다 등록먼저 해주세요", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<ApiCategoryDto.info>>(infolist, HttpStatus.OK);
	}

	@GetMapping("/{categorySeq}")
	public ResponseEntity<?> findOne(ApiCategoryDto.find categoryDto)
	{
		log.debug("selectone 도착");
		ApiCategoryDto.info info = null;
		try
		{
			info = apiCategoryService.selectOne(categoryDto);
		} catch (NullPointerException ne)
		{
			return new ResponseEntity<String>("존재하지 않는 카테고리입니다 카테고리 seq를 다시 확인해주세요", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApiCategoryDto.info>(info, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<?> add(ApiCategoryDto.save categoryDto)
	{
		ApiCategoryDto.info info = null;
		try
		{
			info = apiCategoryService.insertOne(categoryDto);
		} catch (Exception e)
		{
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApiCategoryDto.info>(info, HttpStatus.OK);
	}

	@PostMapping("/edit/{categorySeq}")
	public ApiCategoryDto.info update(ApiCategoryDto.update categoryDto)
	{
		return apiCategoryService.updateOne(categoryDto);
	}

	@PostMapping("/remove/{categorySeq}")
	public ResponseEntity<String> del(ApiCategoryDto.delete categoryDto)
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
