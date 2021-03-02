package com.sqisoft.testproject.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.sqisoft.testproject.domain.DeviceEntity;
import com.sqisoft.testproject.domain.MuseumEntity;
import com.sqisoft.testproject.model.DeviceDto;
import com.sqisoft.testproject.service.DeviceService;
import com.sqisoft.testproject.service.MuseumService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/mus")
public class MuseumController
{

	@Autowired
	MuseumService museumService;

	@Autowired
	DeviceService deviceService;

	@GetMapping("/main")
	public String main(Model model)
	{
		model.addAttribute("devicelist", deviceService.selectAll());
		model.addAttribute("museumlist", museumService.selectAll());
		return "/museum/main";
	}

	@GetMapping("/modify/{museumSeq}")
	public String modify(Model model, @PathVariable Integer museumSeq)
	{
		model.addAttribute("devicelist", deviceService.selectAll());
		model.addAttribute("museum", museumService.selectOne(museumSeq).orElse(null));
		model.addAttribute("deviceSeq", museumService.selectOneDeviceSeq(museumSeq));
		return "/museum/modify";
	}
	
	@PostMapping("/getlist/{deviceSeq}")
	public String selectlist(@PathVariable Integer deviceSeq , Model model) throws IOException
	{
		if(deviceSeq==-1) {
			model.addAttribute("museumlist",museumService.selectAll()); 
			return "/museum/table";
		}
		List<MuseumEntity> list = museumService.selectDeviceList(deviceSeq);
		model.addAttribute("museumlist",list);
		return "/museum/table";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<Boolean> saveOne(DeviceDto deviceDto, MultipartHttpServletRequest mRequest) throws IOException
	{
		if (museumService.insertOne(deviceDto, mRequest))
		{
			return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/modify/{museumSeq}")
	@ResponseBody
	public ResponseEntity<Boolean> modifyOne(@PathVariable Integer museumSeq, DeviceDto deviceDto, MultipartHttpServletRequest mRequest)
					throws IOException
	{
		if (museumService.modifyOne(museumSeq, deviceDto, mRequest))
		{
			return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

	@PostMapping("/remove/{museumSeq}")
	@ResponseBody
	public ResponseEntity<String> deleteOne(@PathVariable Integer museumSeq)
	{
		log.debug(museumSeq.toString());
		museumService.deleteOne(museumSeq);
		return ResponseEntity.ok("삭제 완료");
	}
}
