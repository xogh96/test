package com.sqisoft.testproject.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sqisoft.testproject.domain.CategoryEntity;
import com.sqisoft.testproject.service.CategoryService;
import com.sqisoft.testproject.service.ExhibitService;
import com.sqisoft.testproject.service.MuseumService;

import lombok.extern.slf4j.Slf4j;
import sun.security.provider.certpath.CollectionCertStore;

@Slf4j
@Controller
@RequestMapping("/ex")
public class ExhibitController
{

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	MuseumService museumService;

	@Autowired
	ExhibitService exhibitService;
	
	
	@GetMapping("/main")
	public String main(Model model)
	{
		model.addAttribute("categorylist", categoryService.selectAll());
		model.addAttribute("museumlist", museumService.selectAll());
		return "/exhibit/main";
	}

	@GetMapping("/register/{categorySeq}")
	public String register(Model model , @PathVariable Integer categorySeq)
	{
		model.addAttribute("category" , categoryService.selectOne(categorySeq).orElse(null));
		return "/exhibit/register";
	}
	
	@GetMapping("/modify/{categorySeq}")
	public String modify(Model model , @PathVariable Integer categorySeq)
	{
		model.addAttribute("category" , categoryService.selectOne(categorySeq).orElse(null));
		return "/exhibit/modify";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<Boolean> saveOne(Integer categorySeq, String[] content,MultipartHttpServletRequest mRequest) throws IOException
	{
		if (exhibitService.insertOne(categorySeq, mRequest.getFiles("files"),content))
		{
			return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/getlist/{museumSeq}")
	public String selectlist(@PathVariable Integer museumSeq , Model model) throws IOException
	{
		log.debug("getlist 가 호출되었음");
		if(museumSeq==-1) {
			model.addAttribute("categorylist",categoryService.selectAll()); 
			return "/exhibit/table";
		}
		List<CategoryEntity> list = categoryService.selectBymuseumSeq(museumSeq);
		model.addAttribute("categorylist",list);
		return "/exhibit/table";
	}
	
	@PostMapping("/modify")
	@ResponseBody
	public ResponseEntity<Boolean> modifyOne(Integer categorySeq, String[] content , String[] delete ,MultipartHttpServletRequest mRequest) throws IOException
	{
		exhibitService.deleteone(delete);
		if (exhibitService.modify(categorySeq, content ,mRequest))
		{
			return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
