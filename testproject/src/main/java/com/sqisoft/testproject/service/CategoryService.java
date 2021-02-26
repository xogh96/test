package com.sqisoft.testproject.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqisoft.testproject.config.SqiException;
import com.sqisoft.testproject.domain.CategoryEntity;
import com.sqisoft.testproject.domain.MuseumEntity;
import com.sqisoft.testproject.model.CategoryDto;
import com.sqisoft.testproject.repository.CategoryRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService
{
	@Autowired
	private CategoryRepo categoryRepository;

	@Transactional
	public List<CategoryEntity> selectAll()
	{
		return categoryRepository.findAll();
	}

	@Transactional
	public Optional<CategoryEntity> selectOne(Integer categorySeq)
	{
		return categoryRepository.findById(categorySeq);
	}
	
	
	@Transactional
	public List<CategoryEntity> selectBymuseumSeq(Integer museumSeq)
	{
		List<CategoryEntity> list = categoryRepository.findByMuseumEntityMuseumSeq(museumSeq);
		
		if (list.size() == 0)
		{
			throw new SqiException("데이터가 존재하지 않습니다 등록먼저 해주세요");
		}
		return list;
	}
	
	

	@Transactional
	public boolean insertOne(CategoryDto categoryDto)
	{
		MuseumEntity museumEntity = new MuseumEntity();
		museumEntity.setMuseumSeq(categoryDto.getMuseumSeq());

		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setCategoryName(categoryDto.getCategoryName());
		categoryEntity.setMuseumEntity(museumEntity);

		CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
		if (savedEntity != null && savedEntity.getCategorySeq() > 0)
		{
			return true;
		} else
			return false;
	}

	@Transactional
	public void deleteOne(Integer categorySeq)
	{
		categoryRepository.deleteById(categorySeq);
	}

	@Transactional
	public boolean modifyOne(Integer categorySeq, CategoryDto categoryDto)
	{
		CategoryEntity categoryEntity = categoryRepository.findById(categorySeq).orElse(null);
		categoryEntity.setCategoryName(categoryDto.getCategoryName());

		CategoryEntity savedEntity = categoryRepository.save(categoryEntity);

		if (savedEntity == null)
		{
			return false;
		} else
			return true;
	}

	

}
