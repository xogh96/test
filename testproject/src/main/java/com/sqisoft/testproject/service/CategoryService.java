package com.sqisoft.testproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqisoft.testproject.config.SqiException;
import com.sqisoft.testproject.domain.CategoryEntity;
import com.sqisoft.testproject.domain.DeviceEntity;
import com.sqisoft.testproject.domain.MuseumEntity;
import com.sqisoft.testproject.model.CategoryDto;
import com.sqisoft.testproject.repository.CategoryRepo;
import com.sqisoft.testproject.repository.MuseumRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService
{
	@Autowired
	private CategoryRepo categoryRepository;

	@Autowired
	private MuseumRepo museumRepository;

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
	public List<DeviceEntity> selectDeviceListByMuseumSeq(Integer museumSeq)
	{
		MuseumEntity museumEntity = museumRepository.findById(museumSeq).orElse(null);
		List<DeviceEntity> devicelist = new ArrayList<DeviceEntity>();
		for (int i = 0; i < museumEntity.getDeviceEntity().size(); i++)
		{
			devicelist.add(museumEntity.getDeviceEntity().get(i));
		}
		if (devicelist.size() == 0)
		{
			throw new SqiException("박물관에 디바이스가 없습니다 등록먼저 해주세요");
		}
		return devicelist;
	}

	@Transactional
	public boolean insertOne(CategoryDto categoryDto)
	{
		if(categoryDto.getDeviceSeq()==null || categoryDto.getMuseumSeq()==null) {
			throw new SqiException("데이터를 모두 입력해주세요");
		}
		
		MuseumEntity museumEntity = new MuseumEntity();
		museumEntity.setMuseumSeq(categoryDto.getMuseumSeq());
		
		DeviceEntity deviceEntity = new DeviceEntity();
		deviceEntity.setDeviceSeq(categoryDto.getDeviceSeq());

		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setCategoryName(categoryDto.getCategoryName());
		categoryEntity.setMuseumEntity(museumEntity);
		categoryEntity.setDeviceEntity(deviceEntity);

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
