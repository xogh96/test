package com.sqisoft.testproject.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sqisoft.testproject.config.SqiException;
import com.sqisoft.testproject.domain.CategoryEntity;
import com.sqisoft.testproject.domain.ContentEntity;
import com.sqisoft.testproject.domain.ContentFileEntity;
import com.sqisoft.testproject.repository.CategoryRepo;
import com.sqisoft.testproject.repository.ContentFileRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/file")
public class FileController
{
	@Autowired
	private ContentFileRepo contentFileRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Value("${content.file-path}")
	private String contentPath;

	@GetMapping("/download/{contentSeq}")
	public ResponseEntity<Resource> download(@PathVariable Integer contentSeq) throws IOException
	{
		ContentFileEntity contentFileEntity = contentFileRepo.findById(contentSeq).orElse(null);
		if (contentFileEntity == null)
		{
			throw new SqiException("파일이 존재하지않아 다운로드 받을 수 없습니다");
		}
		Path path = Paths.get(contentPath + File.separator + contentFileEntity.getFilePhyName());

		Resource resource = null;
		try
		{
			resource = new InputStreamResource(Files.newInputStream(path));
		} catch (NoSuchFileException ne)
		{
			throw new SqiException("파일이 존재하지않아 다운로드 받을 수 없습니다");
		}
		// header만들기
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(
						ContentDisposition.builder("attachment").filename(contentFileEntity.getFileName(), StandardCharsets.UTF_8).build());

		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

	@GetMapping("/imageView/{contentSeq}")
	public ResponseEntity<Resource> viewImage(@PathVariable Integer contentSeq) throws IOException
	{
		ContentFileEntity contentFileEntity = contentFileRepo.findById(contentSeq).orElse(null);
		Path path = Paths.get(contentPath + File.separator + "thumb" + File.separator + contentFileEntity.getFileThumbPhyName());
		Resource resource = null;
		try
		{
			resource = new InputStreamResource(Files.newInputStream(path));
		} catch (NoSuchFileException ne)
		{
			return new ResponseEntity<>(resource, HttpStatus.NOT_FOUND);
		}
		HttpHeaders headers = new HttpHeaders();

		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

	@PostMapping("/imageView/{categorySeq}")
	@ResponseBody
	public Object[] getlist(@PathVariable Integer categorySeq) throws IOException
	{
		CategoryEntity categoryEntity = categoryRepo.findById(categorySeq).orElse(null);

		List<ContentEntity> contentList = categoryEntity.getContentEntity();

		List<Integer> filearray = new ArrayList<Integer>();
		List<String> contentarray = new ArrayList<String>();
		
		
		for (int i = 0; i < contentList.size(); i++)
		{
			filearray.add(contentList.get(i).getContentSeq());
			contentarray.add(contentList.get(i).getContentName());
		}
		log.debug(filearray.toString());
		log.debug(contentarray.toString());
		
		Object[] result = new Object[] {filearray , contentarray};
		return result;
	}
}
