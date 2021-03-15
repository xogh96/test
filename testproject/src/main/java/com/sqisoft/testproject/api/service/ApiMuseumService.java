package com.sqisoft.testproject.api.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sqisoft.testproject.api.model.ApiMuseumDto;
import com.sqisoft.testproject.api.repository.ApiMuseumRepo;
import com.sqisoft.testproject.config.SqiException;
import com.sqisoft.testproject.domain.MuseumEntity;
import com.sqisoft.testproject.util.FileUtils;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ApiMuseumService
{
	@Autowired
	private ApiMuseumRepo apiMuseumRepository;

	@Value("${content.file-path}")
	private String contentPath;

	@Autowired
	private FileUtils fileUtils;

	@Transactional
	public List<ApiMuseumDto.museumInfo> selectAll()
	{
		List<ApiMuseumDto.museumInfo> infoList = new ArrayList<ApiMuseumDto.museumInfo>();
		List<MuseumEntity> museumList = apiMuseumRepository.findAll();
		for (int i = 0; i < museumList.size(); i++)
		{
			ApiMuseumDto.museumInfo info = new ApiMuseumDto.museumInfo(museumList.get(i));
			infoList.add(info);
		}
		return infoList;
	}

	@Transactional
	public ApiMuseumDto.museumInfo selectOne(ApiMuseumDto.museumFind museumDto)
	{
		MuseumEntity museumEntity = apiMuseumRepository.findById(museumDto.getMuseumSeq()).orElse(null);
		ApiMuseumDto.museumInfo info = new ApiMuseumDto.museumInfo(museumEntity);
		return info;
	}

	
	/**
	 * @param file
	 * @return Map
	 * @throws IOException
	 *             multipartFile을 넣으면 파일 저장 후 map(정보)을 return 해준다
	 *
	 *             key : FileThumbPhyName , FileName , FilePhyName , FileSize , FileContentType , FileOrder
	 * 
	 * @author 박태호 e-mail: th.park@sqisoft.com
	 * @since 2021. 3. 9.
	 */
	@SuppressWarnings("unused")
	private Map<String, Object> getFileInfos(MultipartFile file) throws IOException
	{

		Map<String, Object> fileInfos = new HashMap<String, Object>();

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

		fileInfos.put("FileThumbPhyName", fileThumbHardName);
		fileInfos.put("FileName", file.getOriginalFilename());
		fileInfos.put("FilePhyName", fileHardName);
		fileInfos.put("FileSize", file.getSize());
		fileInfos.put("FileContentType", contentType);
		fileInfos.put("FileOrder", 0);

		return fileInfos;
	}
}
