package com.sqisoft.testproject.apis.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqisoft.testproject.apis.model.ApiCategoryDto;
import com.sqisoft.testproject.apis.model.ApiContentDto;
import com.sqisoft.testproject.apis.model.ApiContentDto.delete;
import com.sqisoft.testproject.apis.model.ApiContentDto.info;
import com.sqisoft.testproject.apis.model.ApiContentDto.save;
import com.sqisoft.testproject.apis.repository.ApiCategoryRepo;
import com.sqisoft.testproject.apis.repository.ApiContentRepo;
import com.sqisoft.testproject.domain.CategoryEntity;
import com.sqisoft.testproject.domain.ContentEntity;
import com.sqisoft.testproject.domain.ContentFileEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiContentService
{
	@Autowired
	private ApiContentRepo apiContentRepository;

	@Autowired
	private ApiCategoryRepo apiCategoryRepository;

	@Transactional
	public List<ApiContentDto.info> selectAll()
	{
		List<ApiContentDto.info> infoList = new ArrayList<ApiContentDto.info>();
		List<ContentEntity> contentList = apiContentRepository.findAll();
		for (int i = 0; i < contentList.size(); i++)
		{
			ApiContentDto.info info = new ApiContentDto.info(contentList.get(i));
			infoList.add(info);
		}
		return infoList;
	}

	@Transactional
	public ApiContentDto.info selectOne(ApiContentDto.find contentDto)
	{
		ContentEntity contentEntity = apiContentRepository.findById(contentDto.getContentSeq()).orElse(null);
		ApiContentDto.info info = new ApiContentDto.info(contentEntity);
		return info;
	}

	@Transactional
	public ApiContentDto.info insertOne(ApiContentDto.save contentDto)
	{
		ApiContentDto.info data = null;
		// add
		if (contentDto.getContentSeq() == null)
		{
			ContentEntity contentEntity = new ContentEntity();
			contentEntity.setContentName(contentDto.getContentName());
			contentEntity.setCategoryEntity(apiCategoryRepository.findById(contentDto.getCategorySeq()).orElse(null));
			ContentFileEntity contentFileEntity = new ContentFileEntity();
			contentFileEntity.setFileName(contentDto.getFileName());
			contentFileEntity.setFilePhyName(contentDto.getFilePhyName());
			contentFileEntity.setFileThumbPhyName(contentDto.getFileThumbPhyName());
			contentFileEntity.setFileSize(contentDto.getFileSize());
			contentFileEntity.setFileContentType(contentDto.getFileContentType());
			contentFileEntity.setFileOrder(contentDto.getFileOrder());
			contentEntity.setContentFileEntity(contentFileEntity);

			ContentEntity savedEntity = apiContentRepository.save(contentEntity);
			data = new ApiContentDto.info(savedEntity);
		}
		// edit
		if (contentDto.getContentSeq() != null)
		{
			ContentEntity contentEntity = apiContentRepository.findById(contentDto.getContentSeq()).orElse(null);
			// 있으면 바꾸고 없으면 그대로
			if (contentDto.getContentName() != null)
			{
				contentEntity.setContentName(contentDto.getContentName());
			}
			if (contentDto.getCategorySeq() != null)
			{
				contentEntity.setCategoryEntity(apiCategoryRepository.findById(contentDto.getCategorySeq()).orElse(null));
			}
			
			ContentFileEntity contentFileEntity = contentEntity.getContentFileEntity();

			// 추후 multipartfile로 변경
			if (contentDto.getFileName() != null)
			{
				contentFileEntity.setFileName(contentDto.getFileName());
			}

			if (contentDto.getFileContentType() != null)
			{
				contentFileEntity.setFileContentType(contentDto.getFileContentType());
			}

			if (contentDto.getFileName() != null)
			{
				contentFileEntity.setFileName(contentDto.getFileName());
			}

			if (contentDto.getFilePhyName() != null)
			{
				contentFileEntity.setFilePhyName(contentDto.getFilePhyName());
			}

			if (contentDto.getFileThumbPhyName() != null)
			{
				contentFileEntity.setFileThumbPhyName(contentDto.getFileThumbPhyName());
			}

			if (contentDto.getFileSize() != null)
			{
				contentFileEntity.setFileSize(contentDto.getFileSize());
			}
			if (contentDto.getFileOrder() != null)
			{
				contentFileEntity.setFileOrder(contentDto.getFileOrder());
			}
			contentEntity.setContentFileEntity(contentFileEntity);

			ContentEntity savedEntity = apiContentRepository.save(contentEntity);
			data = new ApiContentDto.info(savedEntity);
		}
		return data;
	}

	@Transactional
	public void deleteOne(delete deviceDto)
	{
		apiContentRepository.deleteById(deviceDto.getContentSeq());
	}

}