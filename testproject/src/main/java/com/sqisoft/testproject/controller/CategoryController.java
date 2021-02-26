package com.sqisoft.testproject.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sqisoft.testproject.domain.CategoryEntity;
import com.sqisoft.testproject.domain.MuseumEntity;
import com.sqisoft.testproject.model.CategoryDto;
import com.sqisoft.testproject.model.DeviceDto;
import com.sqisoft.testproject.service.CategoryService;
import com.sqisoft.testproject.service.MuseumService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/cat")
public class CategoryController
{
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	MuseumService museumService;

	@GetMapping("/main")
	public String main(Model model)
	{
		model.addAttribute("categorylist", categoryService.selectAll());
		model.addAttribute("museumlist", museumService.selectAll());
		return "/category/main";
	}
	
	@GetMapping("/modify/{categorySeq}")
	public String modifypage(Model model ,  @PathVariable Integer categorySeq)
	{
		model.addAttribute("category", categoryService.selectOne(categorySeq).orElse(null));
		return "/category/modify";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<Boolean> saveOne(CategoryDto categoryDto) 
	{
		log.debug(categoryDto.toString());
		if (categoryService.insertOne(categoryDto))
		{
			return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/modify/{categorySeq}")
	@ResponseBody
	public ResponseEntity<Boolean> modifyOne(@PathVariable Integer categorySeq,CategoryDto categoryDto)
	{
		log.debug(categoryDto.toString());
		if (categoryService.modifyOne(categorySeq,categoryDto))
		{
			return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/getlist/{museumSeq}")
	public String selectlist(@PathVariable Integer museumSeq , Model model) throws IOException
	{
		if(museumSeq==-1) {
			model.addAttribute("categorylist",categoryService.selectAll()); 
			return "/category/table";
		}
		List<CategoryEntity> list = categoryService.selectBymuseumSeq(museumSeq);
		model.addAttribute("categorylist",list);
		return "/category/table";
	}
	
	@PostMapping("/remove/{categorySeq}")
	@ResponseBody
	public ResponseEntity<String> deleteOne(@PathVariable Integer categorySeq)
	{
		categoryService.deleteOne(categorySeq);
		return ResponseEntity.ok("삭제 완료");
	}

}
