package com.sqisoft.testproject.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sqisoft.testproject.config.SqiException;
import com.sqisoft.testproject.domain.ContentFileEntity;
import com.sqisoft.testproject.domain.DeviceEntity;
import com.sqisoft.testproject.domain.MuseumEntity;
import com.sqisoft.testproject.model.DeviceDto;
import com.sqisoft.testproject.repository.DeviceRepo;
import com.sqisoft.testproject.repository.MuseumRepo;
import com.sqisoft.testproject.util.FileUtils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Service
@Slf4j
public class MuseumService
{
	@Autowired
	private MuseumRepo museumRepository;

	@Value("${content.file-path}")
	private String contentPath;

	@Autowired
	private FileUtils fileUtils;

	@Autowired
	private DeviceService deviceService;

	@Transactional
	public List<MuseumEntity> selectAll()
	{
		return museumRepository.findAll();
	}

	@Transactional
	public Optional<MuseumEntity> selectOne(Integer museumSeq)
	{
		return museumRepository.findById(museumSeq);
	}
	
	@Transactional
	public List<Integer> selectOneDeviceSeq(Integer museumSeq)
	{
		MuseumEntity museumEntity = museumRepository.findById(museumSeq).orElse(null);
		List<DeviceEntity> deviceList = museumEntity.getDeviceEntity();
		List<Integer> deviceSeq = new ArrayList<Integer>();
		
		for (int i = 0; i < deviceList.size(); i++)
		{
			deviceSeq.add(deviceList.get(i).getDeviceSeq());
		}
		return deviceSeq;
	}
	
	public List<MuseumEntity> selectDeviceList(Integer deviceSeq)
	{
		return museumRepository.findByDeviceEntityDeviceSeq(deviceSeq);
	}

	@Transactional
	public boolean modifyOne(Integer museumSeq, DeviceDto deviceDto, MultipartHttpServletRequest mRequest) throws IOException
	{
		List<DeviceEntity> devicelist = new ArrayList<DeviceEntity>();
		// deviceseq로 devicecode랑 name 가져오기
		for (int i = 0; i < deviceDto.getDeviceSeq().length; i++)
		{
			DeviceEntity getDeviceEntity = deviceService.selectOne(deviceDto.getDeviceSeq()[i]).orElse(null);
			devicelist.add(getDeviceEntity);
		}
		
		MuseumEntity museumEntity = museumRepository.findById(museumSeq).orElse(null);
		museumEntity.setMuseumName(deviceDto.getMuseumName());
		museumEntity.setMuseumLoc(deviceDto.getMuseumLoc());
		museumEntity.setMuseumTel(deviceDto.getMuseumTel());
		museumEntity.setDeviceEntity(devicelist);

		// 만약 파일이 들어온게 있다면
		if (!mRequest.getFile("file").isEmpty())
		{
			deviceDto = setDtoInfos(deviceDto, mRequest.getFile("file"));

			ContentFileEntity contentFileEntity = museumEntity.getContentFileEntity();
			contentFileEntity.setFileContentType(deviceDto.getFileContentType());
			contentFileEntity.setFileName(deviceDto.getFileName());
			contentFileEntity.setFileOrder(deviceDto.getFileOrder());
			contentFileEntity.setFilePhyName(deviceDto.getFilePhyName());
			contentFileEntity.setFileSize(deviceDto.getFileSize());
			contentFileEntity.setFileThumbPhyName(deviceDto.getFileThumbPhyName());
		}
		MuseumEntity savedEntity = museumRepository.save(museumEntity);
		if (savedEntity == null)
		{
			return false;
		}
		return true;

	}

	@Transactional
	public boolean insertOne(DeviceDto deviceDto, MultipartHttpServletRequest mRequest) throws IOException
	{
		List<DeviceEntity> devicelist = new ArrayList<DeviceEntity>();
		// deviceseq로 devicecode랑 name 가져오기
		for (int i = 0; i < deviceDto.getDeviceSeq().length; i++)
		{
			DeviceEntity getDeviceEntity = deviceService.selectOne(deviceDto.getDeviceSeq()[i]).orElse(null);
			devicelist.add(getDeviceEntity);
		}
		DeviceDto getdeviceDto = setDtoInfos(deviceDto, mRequest.getFile("file"));

		MuseumEntity museumEntity = new MuseumEntity();

		ContentFileEntity contentFileEntity = new ContentFileEntity();

		contentFileEntity.setFileContentType(getdeviceDto.getFileContentType());
		contentFileEntity.setFileName(getdeviceDto.getFileName());
		contentFileEntity.setFileOrder(getdeviceDto.getFileOrder());
		contentFileEntity.setFilePhyName(getdeviceDto.getFilePhyName());
		contentFileEntity.setFileSize(getdeviceDto.getFileSize());
		contentFileEntity.setFileThumbPhyName(getdeviceDto.getFileThumbPhyName());

		museumEntity.setMuseumLoc(getdeviceDto.getMuseumLoc());
		museumEntity.setMuseumName(getdeviceDto.getMuseumName());
		museumEntity.setMuseumTel(getdeviceDto.getMuseumTel());

		museumEntity.setContentFileEntity(contentFileEntity);

		museumEntity.setDeviceEntity(devicelist);

		MuseumEntity savedEntity = museumRepository.save(museumEntity);

		if (savedEntity == null)
		{
			return false;
		}
		return true;
	}

	@Transactional
	public void deleteOne(Integer museumSeq)
	{
		museumRepository.deleteById(museumSeq);
	}

	/**
	 * file을 넣으면 dto를 세팅해서 return해주는 메소드
	 * 
	 * @param deviceDto
	 * @param file
	 * @return DeviceDto
	 * @throws IOException
	 */
	private DeviceDto setDtoInfos(DeviceDto deviceDto, MultipartFile file) throws IOException
	{
		// validations
		if (file.isEmpty())
		{
			throw new SqiException("로고파일이 없습니다.");
		}
		String contentType = fileUtils.getContentType(file.getInputStream());
		if (!StringUtils.startsWith(contentType, "image"))
		{
			throw new SqiException("이미지 파일만 업로드 가능합니다.");
		}

		// 폴더만들기
		File dir = new File(contentPath);
		File thumbdir = new File(contentPath + File.separator + "thumb");
		// + File.separator + SqiUtils.getYYYYmm() //날짜별로 관리하고 싶을때는 날짜를 넣어주기
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		if (!thumbdir.exists())
		{
			thumbdir.mkdirs();
		}
		// 파일 실제파일이름(폴더에 저장될 이름)
		final String fileHardName = (UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
		final String fileThumbHardName = (UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));

		// 폴더에 방금만든 실제파일이름 으로 파일을 만든다 readonly , executable
		File saveFile = new File(dir.getCanonicalPath(), fileHardName);
		File saveThumbFile = new File(thumbdir.getCanonicalPath(), fileThumbHardName);

		// 읽기전용 ,파일소유자의 실행권한 관련 세팅을 해준다
		// saveFile.setReadOnly();
		// saveFile.setExecutable(false, false);

		// 가져온 file을 saveFile로 transferTo해준다 (가져온 file에 설정해놓은 savefile로 받은파일을 씌워주는 과정)
		file.transferTo(saveFile);

		Thumbnails.of(saveFile).size(300, 100).toFile(saveThumbFile);

		// dto에 넣어준다
		deviceDto.setFileContentType(contentType);
		deviceDto.setFileName(file.getOriginalFilename());
		deviceDto.setFilePhyName(fileHardName);
		deviceDto.setFileThumbPhyName(fileThumbHardName);
		deviceDto.setFileSize(file.getSize());
		deviceDto.setFileOrder(0);
		return deviceDto;
	}

	


}
