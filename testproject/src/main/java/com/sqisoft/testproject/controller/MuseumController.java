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
		model.addAttribute("deviceEmptyList", museumService.selectAllByEmptyMuseumEntity());
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
	public String selectlist(@PathVariable Integer deviceSeq, Model model) throws IOException
	{
		List<MuseumEntity> list;
		if (deviceSeq == -1)
		{
			list = museumService.selectAll();
		} else
		{
			list = museumService.selectDeviceList(deviceSeq);
		}
		model.addAttribute("museumlist", list);
		return "/museum/table";
	}

	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<Boolean> saveOne(DeviceDto deviceDto, MultipartHttpServletRequest mRequest) throws IOException
	{
		boolean isSuccess = museumService.insertOne(deviceDto, mRequest);
		return new ResponseEntity<Boolean>(isSuccess, isSuccess ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/modify/{museumSeq}")
	@ResponseBody
	public ResponseEntity<Boolean> modifyOne(@PathVariable Integer museumSeq, DeviceDto deviceDto, MultipartHttpServletRequest mRequest)
					throws IOException
	{
		boolean isSuccess = museumService.modifyOne(museumSeq, deviceDto, mRequest);
		return new ResponseEntity<Boolean>(isSuccess, isSuccess ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
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
