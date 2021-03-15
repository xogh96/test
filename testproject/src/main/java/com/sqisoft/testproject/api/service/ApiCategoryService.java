package com.sqisoft.testproject.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqisoft.testproject.api.model.ApiCategoryDto;
import com.sqisoft.testproject.api.repository.ApiCategoryRepo;
import com.sqisoft.testproject.domain.CategoryEntity;

@Service
public class ApiCategoryService
{
	@Autowired
	private ApiCategoryRepo apiCategoryRepository;

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
	public ApiCategoryDto.categoryInfo selectOne(ApiCategoryDto.categoryFind categoryDto)
	{
		CategoryEntity categoryEntity = apiCategoryRepository.findById(categoryDto.getCategorySeq()).orElse(null);
		ApiCategoryDto.categoryInfo info = new ApiCategoryDto.categoryInfo(categoryEntity);
		return info;
	}
}
