package com.sqisoft.testproject.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqisoft.testproject.api.model.ApiCategoryDto;
import com.sqisoft.testproject.api.repository.ApiCategoryRepo;
import com.sqisoft.testproject.api.repository.ApiMuseumRepo;
import com.sqisoft.testproject.domain.CategoryEntity;
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

	@Transactional
	public List<ApiCategoryDto.categoryInfo> selectAll()
	{
		List<ApiCategoryDto.categoryInfo> infoList = new ArrayList<ApiCategoryDto.categoryInfo>();
		List<CategoryEntity> list = apiCategoryRepository.findAll();
		for (int i = 0; i < list.size(); i++)
		{
			ApiCategoryDto.categoryInfo info = new ApiCategoryDto.categoryInfo(list.get(i));
			infoList.add(info);
		}
		return infoList;
	}

	@Transactional
	public ApiCategoryDto.categoryInfo insertOne(ApiCategoryDto.categorySave categoryDto)
	{
		ApiCategoryDto.categoryInfo data = null;
		// add
		MuseumEntity museumEntity = apiMuseumRepository.findById(categoryDto.getMuseumSeq()).orElse(null);
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setCategoryName(categoryDto.getCategoryName());
		categoryEntity.setMuseumEntity(museumEntity);
		CategoryEntity savedData = apiCategoryRepository.save(categoryEntity);
		data = new ApiCategoryDto.categoryInfo(savedData);
		return data;
	}

	@Transactional
	public ApiCategoryDto.categoryInfo updateOne(ApiCategoryDto.categoryUpdate categoryDto)
	{
		ApiCategoryDto.categoryInfo data = null;

		CategoryEntity categoryEntity = apiCategoryRepository.findById(categoryDto.getCategorySeq()).orElse(null);

		// 들어온 데이터가 있으면 바꾸고 없으면 안바꾼다
		if (categoryDto.getCategoryName() != null)
		{
			categoryEntity.setCategoryName(categoryDto.getCategoryName());
		}
		if (categoryDto.getMuseumSeq() != null)
		{
			MuseumEntity museumEntity = apiMuseumRepository.findById(categoryDto.getMuseumSeq()).orElse(null);
			categoryEntity.setMuseumEntity(museumEntity);
		}
		CategoryEntity savedData = apiCategoryRepository.save(categoryEntity);
		data = new ApiCategoryDto.categoryInfo(savedData);
		return data;
	}

	@Transactional
	public ApiCategoryDto.categoryInfo selectOne(ApiCategoryDto.categoryFind categoryDto)
	{
		CategoryEntity categoryEntity = apiCategoryRepository.findById(categoryDto.getCategorySeq()).orElse(null);
		ApiCategoryDto.categoryInfo info = new ApiCategoryDto.categoryInfo(categoryEntity);
		return info;
	}

	@Transactional
	public void deleteOne(ApiCategoryDto.categoryDelete categoryDto)
	{
		apiCategoryRepository.deleteById(categoryDto.getCategorySeq());
	}

}
