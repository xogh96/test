package com.sqisoft.testproject.apis.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqisoft.testproject.apis.model.ApiCategoryDto;
import com.sqisoft.testproject.apis.model.ApiCategoryDto.delete;
import com.sqisoft.testproject.apis.repository.ApiCategoryRepo;
import com.sqisoft.testproject.apis.repository.ApiDeviceRepo;
import com.sqisoft.testproject.apis.repository.ApiMuseumRepo;
import com.sqisoft.testproject.domain.CategoryEntity;
import com.sqisoft.testproject.domain.DeviceEntity;
import com.sqisoft.testproject.domain.MuseumEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiCategoryService
{
	@Autowired
	private ApiCategoryRepo apiCategoryRepository;

	@Autowired
	private ApiMuseumRepo apiMuseumRepository;

	@Autowired
	private ApiDeviceRepo apiDeviceRepository;

	@Transactional
	public List<ApiCategoryDto.info> selectAll()
	{
		List<ApiCategoryDto.info> infoList = new ArrayList<ApiCategoryDto.info>();
		List<CategoryEntity> list = apiCategoryRepository.findAll();
		for (int i = 0; i < list.size(); i++)
		{
			ApiCategoryDto.info info = new ApiCategoryDto.info(list.get(i));
			infoList.add(info);
		}
		return infoList;
	}

	@Transactional
	public ApiCategoryDto.info insertOne(ApiCategoryDto.save categoryDto)
	{
		ApiCategoryDto.info data = null;
		// add
		if (categoryDto.getCategorySeq() == null)
		{
			MuseumEntity museumEntity = apiMuseumRepository.findById(categoryDto.getMuseumSeq()).orElse(null);
			DeviceEntity deviceEntity = apiDeviceRepository.findById(categoryDto.getDeviceSeq()).orElse(null);
			CategoryEntity categoryEntity = new CategoryEntity();
			categoryEntity.setCategoryName(categoryDto.getCategoryName());
			categoryEntity.setDeviceEntity(deviceEntity);
			categoryEntity.setMuseumEntity(museumEntity);
			CategoryEntity savedData = apiCategoryRepository.save(categoryEntity);
			data = new ApiCategoryDto.info(savedData);
		}
		// update
		if (categoryDto.getCategorySeq() != null)
		{
			CategoryEntity categoryEntity = apiCategoryRepository.findById(categoryDto.getCategorySeq()).orElse(null);
			
			// 들어온 데이터가 있으면 바꾸고 없으면 안바꾼다
			if (categoryDto.getCategoryName() != null)
			{
				categoryEntity.setCategoryName(categoryDto.getCategoryName());
			}
			if (categoryDto.getDeviceSeq() != null)
			{
				DeviceEntity deviceEntity = apiDeviceRepository.findById(categoryDto.getDeviceSeq()).orElse(null);
				categoryEntity.setDeviceEntity(deviceEntity);
			}
			if (categoryDto.getMuseumSeq() != null)
			{
				MuseumEntity museumEntity = apiMuseumRepository.findById(categoryDto.getMuseumSeq()).orElse(null);
				categoryEntity.setMuseumEntity(museumEntity);
			}
			CategoryEntity savedData = apiCategoryRepository.save(categoryEntity);
			data = new ApiCategoryDto.info(savedData);
		}
		return data;
	}

	@Transactional
	public ApiCategoryDto.info selectOne(ApiCategoryDto.find categoryDto)
	{
		CategoryEntity categoryEntity = apiCategoryRepository.findById(categoryDto.getCategorySeq()).orElse(null);
		ApiCategoryDto.info info = new ApiCategoryDto.info(categoryEntity);
		return info;
	}

	@Transactional
	public void deleteOne(delete categoryDto)
	{
		apiCategoryRepository.deleteById(categoryDto.getCategorySeq());
	}

}
