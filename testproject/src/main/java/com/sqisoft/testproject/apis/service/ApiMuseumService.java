package com.sqisoft.testproject.apis.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqisoft.testproject.apis.model.ApiMuseumDto;
import com.sqisoft.testproject.apis.repository.ApiDeviceRepo;
import com.sqisoft.testproject.apis.repository.ApiMuseumRepo;
import com.sqisoft.testproject.domain.ContentFileEntity;
import com.sqisoft.testproject.domain.DeviceEntity;
import com.sqisoft.testproject.domain.MuseumEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiMuseumService
{
	@Autowired
	private ApiMuseumRepo apiMuseumRepository;

	@Autowired
	private ApiDeviceRepo apiDeviceRepository;

	@Transactional
	public List<ApiMuseumDto.info> selectAll()
	{
		List<ApiMuseumDto.info> infoList = new ArrayList<ApiMuseumDto.info>();
		List<MuseumEntity> museumList = apiMuseumRepository.findAll();
		for (int i = 0; i < museumList.size(); i++)
		{
			ApiMuseumDto.info info = new ApiMuseumDto.info(museumList.get(i));
			infoList.add(info);
		}
		return infoList;
	}

	@Transactional
	public ApiMuseumDto.info selectOne(ApiMuseumDto.find museumDto)
	{
		MuseumEntity museumEntity = apiMuseumRepository.findById(museumDto.getMuseumSeq()).orElse(null);
		ApiMuseumDto.info info = new ApiMuseumDto.info(museumEntity);
		return info;
	}

	@Transactional
	public ApiMuseumDto.info insertOne(ApiMuseumDto.save museumDto)
	{
		ApiMuseumDto.info info = null;
		MuseumEntity museumEntity = new MuseumEntity();
		
		// insert
		if (museumDto.getMuseumSeq() == null)
		{
			// dto 에서 contentFileEntity 가져오기
			ContentFileEntity contentFileEntity = new ContentFileEntity();
			contentFileEntity.setFileName(museumDto.getFileName());
			contentFileEntity.setFilePhyName(museumDto.getFilePhyName());
			contentFileEntity.setFileThumbPhyName(museumDto.getFileThumbPhyName());
			contentFileEntity.setFileSize(museumDto.getFileSize());
			contentFileEntity.setFileContentType(museumDto.getFileContentType());
			contentFileEntity.setFileOrder(museumDto.getFileOrder());

			// dto에서 deviceEntity 가져오기
			List<DeviceEntity> list = new ArrayList<DeviceEntity>();
			for (int i = 0; i < museumDto.getDeviceSeq().length; i++)
			{
				DeviceEntity deviceEntity = apiDeviceRepository.findById(museumDto.getDeviceSeq()[i]).orElse(null);
				list.add(deviceEntity);
			}
			museumEntity = new MuseumEntity();

			museumEntity.setMuseumLoc(museumDto.getMuseumLoc());
			museumEntity.setMuseumName(museumDto.getMuseumName());
			museumEntity.setMuseumTel(museumDto.getMuseumTel());
			museumEntity.setDeviceEntity(list);
			museumEntity.setContentFileEntity(contentFileEntity);
		}
		// update
		if (museumDto.getMuseumSeq() != null)
		{
			// 만약 dto에 museum정보가 존재하면 바꾸기 아니면 그대로
			museumEntity = apiMuseumRepository.findById(museumDto.getMuseumSeq()).orElse(null);
			if (museumDto.getMuseumLoc() != null)
			{
				museumEntity.setMuseumLoc(museumDto.getMuseumLoc());
			}
			if (museumDto.getMuseumName() != null)
			{
				museumEntity.setMuseumName(museumDto.getMuseumName());
			}
			if (museumDto.getMuseumTel() != null)
			{
				museumEntity.setMuseumTel(museumDto.getMuseumTel());
			}

			// 만약 dto에 deviceseq가 존재하면 바꾸기 아니면 그대로
			List<DeviceEntity> deviceEntity = museumEntity.getDeviceEntity();
			if (museumDto.getDeviceSeq() != null)
			{
				// dto에서 deviceEntity 가져오기
				List<DeviceEntity> list = new ArrayList<DeviceEntity>();
				for (int i = 0; i < museumDto.getDeviceSeq().length; i++)
				{
					DeviceEntity device = apiDeviceRepository.findById(museumDto.getDeviceSeq()[i]).orElse(null);
					list.add(device);
					deviceEntity = list;
				}
				museumEntity.setDeviceEntity(deviceEntity);
			}

			// 만약 dto에 file이 존재하면 바꾸기 아니면 그대로
			ContentFileEntity contentFileEntity = museumEntity.getContentFileEntity();
			// 추후 multipartfile로 변경
			if (museumDto.getFileName() != null)
			{
				contentFileEntity.setFileName(museumDto.getFileName());
			}
			if (museumDto.getFileContentType() != null)
			{
				contentFileEntity.setFileContentType(museumDto.getFileContentType());
			}

			if (museumDto.getFileName() != null)
			{
				contentFileEntity.setFileName(museumDto.getFileName());
			}

			if (museumDto.getFilePhyName() != null)
			{
				contentFileEntity.setFilePhyName(museumDto.getFilePhyName());
			}

			if (museumDto.getFileThumbPhyName() != null)
			{
				contentFileEntity.setFileThumbPhyName(museumDto.getFileThumbPhyName());
			}

			if (museumDto.getFileSize() != null)
			{
				contentFileEntity.setFileSize(museumDto.getFileSize());
			}
			if (museumDto.getFileOrder() != null)
			{
				contentFileEntity.setFileOrder(museumDto.getFileOrder());
			}
			museumEntity.setContentFileEntity(contentFileEntity);
		}
		MuseumEntity savedDeviceEntity = apiMuseumRepository.save(museumEntity);
		info = new ApiMuseumDto.info(savedDeviceEntity);
		return info;
	}

	@Transactional
	public void deleteOne(ApiMuseumDto.delete museumDto)
	{
		apiMuseumRepository.deleteById(museumDto.getMuseumSeq());
	}
}
