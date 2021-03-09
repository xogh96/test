package com.sqisoft.testproject.apis.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sqisoft.testproject.apis.model.ApiMuseumDto;
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

	@PostMapping("/add")
	public ApiMuseumDto.info add(ApiMuseumDto.save museumDto) throws IOException
	{
		return apiMuseumService.insertOne(museumDto);
	}

	@PostMapping("/edit/{museumSeq}")
	public ApiMuseumDto.info add(ApiMuseumDto.update museumDto) throws IOException
	{
		return apiMuseumService.updateOne(museumDto);
	}

	@PostMapping("/remove/{museumSeq}")
	public void del(ApiMuseumDto.delete museumDto)
	{
		apiMuseumService.deleteOne(museumDto);
	}
}
