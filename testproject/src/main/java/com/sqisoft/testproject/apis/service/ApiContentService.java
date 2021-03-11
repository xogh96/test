package com.sqisoft.testproject.apis.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sqisoft.testproject.apis.model.ApiContentDto;
import com.sqisoft.testproject.apis.repository.ApiCategoryRepo;
import com.sqisoft.testproject.apis.repository.ApiContentRepo;
import com.sqisoft.testproject.config.SqiException;
import com.sqisoft.testproject.domain.ContentEntity;
import com.sqisoft.testproject.domain.ContentFileEntity;
import com.sqisoft.testproject.util.FileUtils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Service
@Slf4j
public class ApiContentService
{
	@Autowired
	private ApiContentRepo apiContentRepository;

	@Autowired
	private ApiCategoryRepo apiCategoryRepository;

	@Value("${content.file-path}")
	private String contentPath;

	@Value("${content.ffmpeg-path}")
	private String ffmpegPath;

	@Autowired
	private FileUtils fileUtils;

	@Transactional
	public List<ApiContentDto.contentInfo> selectAll()
	{
		List<ApiContentDto.contentInfo> infoList = new ArrayList<ApiContentDto.contentInfo>();
		List<ContentEntity> contentList = apiContentRepository.findAll();
		for (int i = 0; i < contentList.size(); i++)
		{
			ApiContentDto.contentInfo info = new ApiContentDto.contentInfo(contentList.get(i));
			infoList.add(info);
		}
		return infoList;
	}

	@Transactional
	public ResponseEntity<Resource> downloadOne(ApiContentDto.contentFind contentDto) throws IOException
	{
		ContentEntity contentEntity = apiContentRepository.findById(contentDto.getContentSeq()).orElse(null);
		Path path = Paths.get(contentPath + File.separator + contentEntity.getContentFileEntity().getFilePhyName());
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		
		// header만들기
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(
						ContentDisposition.builder("attachment").filename(contentEntity.getContentFileEntity().getFileName(), StandardCharsets.UTF_8).build());
		
		return new ResponseEntity<>(resource , headers , HttpStatus.OK);
	}

	@Transactional
	public ApiContentDto.contentInfo selectOne(ApiContentDto.contentFind contentDto)
	{
		ContentEntity contentEntity = apiContentRepository.findById(contentDto.getContentSeq()).orElse(null);
		ApiContentDto.contentInfo info = new ApiContentDto.contentInfo(contentEntity);
		return info;
	}

	@Transactional
	public ApiContentDto.contentInfo insertOne(ApiContentDto.contentSave getcontentDto) throws IOException
	{
		ApiContentDto.contentInfo data = null;
		ApiContentDto.contentSave contentDto = getcontentDto;

		ContentEntity contentEntity = new ContentEntity();
		contentEntity.setContentName(contentDto.getContentName());
		contentEntity.setCategoryEntity(apiCategoryRepository.findById(contentDto.getCategorySeq()).orElse(null));

		if (getcontentDto.getFile() != null)
		{
			Map<String, Object> fileInfoMap = getFileInfos(getcontentDto.getFile());

			ContentFileEntity contentFileEntity = new ContentFileEntity();
			contentFileEntity.setFileName((String) fileInfoMap.get("FileName"));
			contentFileEntity.setFilePhyName((String) fileInfoMap.get("FilePhyName"));
			contentFileEntity.setFileThumbPhyName((String) fileInfoMap.get("FileThumbPhyName"));
			contentFileEntity.setFileSize((Long) fileInfoMap.get("FileSize"));
			contentFileEntity.setFileContentType((String) fileInfoMap.get("FileContentType"));
			contentFileEntity.setFileOrder((Integer) fileInfoMap.get("FileOrder"));
			contentEntity.setContentFileEntity(contentFileEntity);
		}

		ContentEntity savedEntity = apiContentRepository.save(contentEntity);
		data = new ApiContentDto.contentInfo(savedEntity);
		return data;
	}

	@Transactional
	public ApiContentDto.contentInfo updateOne(ApiContentDto.contentUpdate getcontentDto) throws IOException
	{
		ApiContentDto.contentInfo data = null;
		ApiContentDto.contentUpdate contentDto = getcontentDto;

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

		if (getcontentDto.getFile() != null)
		{
			Map<String, Object> fileInfoMap = getFileInfos(getcontentDto.getFile());

			contentFileEntity.setFileName((String) fileInfoMap.get("FileName"));
			contentFileEntity.setFilePhyName((String) fileInfoMap.get("FilePhyName"));
			contentFileEntity.setFileThumbPhyName((String) fileInfoMap.get("FileThumbPhyName"));
			contentFileEntity.setFileSize((Long) fileInfoMap.get("FileSize"));
			contentFileEntity.setFileContentType((String) fileInfoMap.get("FileContentType"));
			contentFileEntity.setFileOrder((Integer) fileInfoMap.get("FileOrder"));
		}
		contentEntity.setContentFileEntity(contentFileEntity);

		ContentEntity savedEntity = apiContentRepository.save(contentEntity);
		data = new ApiContentDto.contentInfo(savedEntity);
		return data;
	}

	@Transactional
	public void deleteOne(ApiContentDto.contentDelete contentDto)
	{
		apiContentRepository.deleteById(contentDto.getContentSeq());
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 *             multipartFile을 넣으면 파일 저장 후 map(정보)를 return 해준다(동영상 가능)
	 *
	 *             key : FileThumbPhyName , FileName , FilePhyName , FileSize , FileContentType , FileOrder
	 * @author 박태호 e-mail: th.park@sqisoft.com
	 * @since 2021. 3. 9.
	 */
	private Map<String, Object> getFileInfos(MultipartFile file) throws IOException
	{

		Map<String, Object> fileInfos = new HashMap<String, Object>();

		if (file.isEmpty())
		{
			throw new SqiException("파일이 없습니다.");
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
		String contentType = fileUtils.getContentType(file.getInputStream());
		if (StringUtils.startsWith(contentType, "image") || StringUtils.startsWith(contentType, "video"))
		{
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

			// 이미지 파일 일 때만 썸네일 생성하기
			if (StringUtils.startsWith(contentType, "image"))
			{
				Thumbnails.of(saveFile).size(200, 180).toFile(saveThumbFile);
				fileInfos.put("FileThumbPhyName", fileThumbHardName);
			}

			if (StringUtils.startsWith(contentType, "video"))
			{
				log.debug("ffmpegpath = " + ffmpegPath);
				Runtime run = Runtime.getRuntime();
				String input = saveFile.getCanonicalPath();
				String output = saveThumbFile.getCanonicalPath();
				String vd = String.format("%s -i \"%s\" -r 4 -t 5 -s 200x180 \"%s.gif\"", ffmpegPath, input, output);
				try
				{
					run.exec("cmd.exe chcp 65001");
					log.debug(vd);
					Process process = run.exec(vd);
					process.waitFor();
					fileInfos.put("FileThumbPhyName", fileThumbHardName + ".gif");
				} catch (Exception e)
				{
					log.debug(e.toString());
					throw new SqiException("동영상 썸네일 생성 실패했습니다 다시 확인해주세요");
				}
			}
			fileInfos.put("FileName", file.getOriginalFilename());
			fileInfos.put("FilePhyName", fileHardName);
			fileInfos.put("FileSize", file.getSize());
			fileInfos.put("FileContentType", contentType);
			fileInfos.put("FileOrder", 0);
		} else
		{
			throw new SqiException("이미지 또는 동영상만 업로드 가능합니다.");
		}
		return fileInfos;
	}

}