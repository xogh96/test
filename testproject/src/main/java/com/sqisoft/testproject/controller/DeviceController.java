package com.sqisoft.testproject.controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sqisoft.testproject.config.SqiException;
import com.sqisoft.testproject.domain.DeviceEntity;
import com.sqisoft.testproject.model.DeviceDto;
import com.sqisoft.testproject.service.DeviceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/dvc")
public class DeviceController
{

	@Autowired
	DeviceService deviceService;

	@GetMapping("/main")
	public String getAll(Model model)
	{
		model.addAttribute("devicelist", deviceService.selectAll());
		return "/device/main";
	}
	
	@GetMapping("/modify/{deviceSeq}")
	public String modifypage(Model model ,  @PathVariable Integer deviceSeq)
	{
		model.addAttribute("device", deviceService.selectOne(deviceSeq).orElse(null));
		return "/device/modify";
	}

	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<Boolean> saveOne(DeviceEntity deviceEntity) throws IOException
	{
		if (deviceService.insertOne(deviceEntity))
		{
			return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/modify/{deviceSeq}")
	@ResponseBody
	public ResponseEntity<Boolean> modifyOne(@PathVariable Integer deviceSeq,DeviceEntity deviceEntity) throws IOException
	{
		if (deviceService.modifyOne(deviceSeq,deviceEntity))
		{
			return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/remove/{deviceSeq}")
	@ResponseBody
	public ResponseEntity<String> deleteOne(@PathVariable Integer deviceSeq)
	{
		try
		{
			deviceService.deleteOne(deviceSeq);
		} catch (DataIntegrityViolationException e)
		{
			throw new SqiException("적용된 박물관이 있어 제거할 수 없습니다");
		}
		return ResponseEntity.ok("삭제 완료");
	}
}
