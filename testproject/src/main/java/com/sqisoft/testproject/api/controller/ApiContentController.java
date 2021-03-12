package com.sqisoft.testproject.api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqisoft.testproject.api.model.ApiContentDto;
import com.sqisoft.testproject.api.service.ApiContentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag( name="content" , description = "콘텐츠 관련 api입니다")
@Slf4j
@RequestMapping("/api/content")
@RestController
public class ApiContentController
{
	@Autowired
	private ApiContentService apiContentService;

	@GetMapping("")
	public List<ApiContentDto.contentInfo> findAll()
	{
		return apiContentService.selectAll();
	}

	@GetMapping("/{contentSeq}")
	public ApiContentDto.contentInfo findOne(ApiContentDto.contentFind contentDto)
	{
		return apiContentService.selectOne(contentDto);
	}

	@PostMapping("/add")
	public ApiContentDto.contentInfo add(ApiContentDto.contentSave contentDto) throws IOException
	{
		return apiContentService.insertOne(contentDto);
	}

	@PostMapping("/edit/{contentSeq}")
	public ApiContentDto.contentInfo update(ApiContentDto.contentUpdate contentDto) throws IOException
	{
		return apiContentService.updateOne(contentDto);
	}

	@PostMapping("/remove/{contentSeq}")
	public void del(ApiContentDto.contentDelete contentDto)
	{
		apiContentService.deleteOne(contentDto);
	}

	@GetMapping("/download/{contentSeq}")
	public ResponseEntity<Resource> download(ApiContentDto.contentFind contentDto) throws IOException
	{
		return apiContentService.downloadOne(contentDto);
	}
}
