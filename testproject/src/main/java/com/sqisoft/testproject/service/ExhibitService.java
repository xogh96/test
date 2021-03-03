package com.sqisoft.testproject.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import com.sqisoft.testproject.domain.CategoryEntity;
import com.sqisoft.testproject.domain.ContentEntity;
import com.sqisoft.testproject.domain.ContentFileEntity;
import com.sqisoft.testproject.model.FileInfoDto;
import com.sqisoft.testproject.repository.CategoryRepo;
import com.sqisoft.testproject.repository.ContentRepo;
import com.sqisoft.testproject.util.FileUtils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Service
@Slf4j
public class ExhibitService
{
	@Autowired
	private ContentRepo contentRepository;

	@Autowired
	private CategoryRepo categoryRepository;

	@Value("${content.file-path}")
	private String contentPath;

	@Autowired
	private FileUtils fileUtils;

	@Transactional
	public boolean insertOne(Integer categorySeq, List<MultipartFile> files, String[] content) throws IOException
	{
		CategoryEntity categoryEntity = categoryRepository.findById(categorySeq).orElse(null);

		ContentEntity savedEntity = new ContentEntity();

		List<FileInfoDto> filelist = setFileInfos(files, categoryEntity);

		for (int j = 0; j < content.length; j++)
		{
			ContentEntity contentEntity = new ContentEntity();
			contentEntity.setContentName(content[j]);
			ContentFileEntity contentFileEntity = new ContentFileEntity();

			contentFileEntity.setFileContentType(filelist.get(j).getFileContentType());
			contentFileEntity.setFileName(filelist.get(j).getFileName());
			contentFileEntity.setFileOrder(filelist.get(j).getFileOrder());
			contentFileEntity.setFilePhyName(filelist.get(j).getFilePhyName());
			contentFileEntity.setFileSize(filelist.get(j).getFileSize());
			contentFileEntity.setFileThumbPhyName(filelist.get(j).getFileThumbPhyName());

			contentEntity.setContentFileEntity(contentFileEntity);
			contentEntity.setCategoryEntity(categoryEntity);
			savedEntity = contentRepository.save(contentEntity);
			if (savedEntity == null)
			{
				return false;
			}
		}
		return true;
	}

	@Transactional
	public void deleteone(String[] delete) throws IOException
	{
		List<String> realdelete = new ArrayList<String>();
		for (int j = 0; j < delete.length; j++)
		{
			realdelete.add(delete[j]);
		}
		// 삭제요청했던거 삭제
		for (int i = 0; i < realdelete.size(); i++)
		{
			contentRepository.deleteById(Integer.parseInt(realdelete.get(i)));
		}
	}

	@Transactional
	public boolean modify(Integer categorySeq, String[] content, MultipartHttpServletRequest mRequest) throws IOException
	{
		CategoryEntity categoryEntity = categoryRepository.findById(categorySeq).orElse(null);
		ContentEntity savedEntity = new ContentEntity();

		List<MultipartFile> realfile = new ArrayList<MultipartFile>();
		List<String> realcontent = new ArrayList<String>();

		for (int i = 0; i < mRequest.getFiles("files").size(); i++)
		{
			realfile.add(mRequest.getFiles("files").get(i));
		}
		for (int j = content.length - 1; j >= content.length - mRequest.getFiles("files").size(); j--)
		{
			realcontent.add(content[j]);
		}

		Collections.reverse(realcontent);

		List<FileInfoDto> filelist = setFileInfos(realfile, categoryEntity);

		// 바뀐거저장
		for (int i = 0; i < realcontent.size(); i++)
		{
			ContentEntity contentEntity = new ContentEntity();
			contentEntity.setContentName(realcontent.get(i));

			ContentFileEntity contentFileEntity = new ContentFileEntity();

			contentFileEntity.setFileContentType(filelist.get(i).getFileContentType());
			contentFileEntity.setFileName(filelist.get(i).getFileName());
			contentFileEntity.setFileOrder(filelist.get(i).getFileOrder());
			contentFileEntity.setFilePhyName(filelist.get(i).getFilePhyName());
			contentFileEntity.setFileSize(filelist.get(i).getFileSize());
			contentFileEntity.setFileThumbPhyName(filelist.get(i).getFileThumbPhyName());

			contentEntity.setContentFileEntity(contentFileEntity);
			contentEntity.setCategoryEntity(categoryEntity);

			savedEntity = contentRepository.save(contentEntity);
			if (savedEntity == null)
			{
				return false;
			}
		}

		return true;
	}

	private List<FileInfoDto> setFileInfos(List<MultipartFile> files, CategoryEntity categoryEntity) throws IOException
	{

//		if (exist == 0)
//		{
//			// validations
//			if (files.isEmpty())
//			{
//				throw new SqiException("파일이 없습니다. 등록해주세요");
//			}
//		}

		int exist = categoryEntity.getContentEntity().size();
		if (exist + files.size() > 10)
		{
			throw new SqiException("작품의 최대 갯수는 10개 입니다");
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

		List<FileInfoDto> fileinfo = new ArrayList<FileInfoDto>();

		int totaltime= 0 ;
		
		for (int i = 0; i < files.size(); i++)
		{
			String contentType = fileUtils.getContentType(files.get(i).getInputStream());
			if (StringUtils.startsWith(contentType, "image") || StringUtils.startsWith(contentType, "video"))
			{
				// 파일 실제파일이름(폴더에 저장될 이름)
				final String fileHardName = (UUID.randomUUID().toString() + "."
								+ FilenameUtils.getExtension(files.get(i).getOriginalFilename()));
				final String fileThumbHardName = (UUID.randomUUID().toString() + "."
								+ FilenameUtils.getExtension(files.get(i).getOriginalFilename()));

				// 폴더에 방금만든 실제파일이름 으로 파일을 만든다 readonly , executable
				File saveFile = new File(dir.getCanonicalPath(), fileHardName);
				File saveThumbFile = new File(thumbdir.getCanonicalPath(), fileThumbHardName);

				// 읽기전용 ,파일소유자의 실행권한 관련 세팅을 해준다
				// saveFile.setReadOnly();
				// saveFile.setExecutable(false, false);
				// 가져온 file을 saveFile로 transferTo해준다 (가져온 file에 설정해놓은 savefile로 받은파일을 씌워주는 과정)
				files.get(i).transferTo(saveFile);

				// 이미지 파일 일 때만 썸네일 생성하기
				if (StringUtils.startsWith(contentType, "image"))
				{
					int start = getCurrentSeconds();
					Thumbnails.of(saveFile).size(200, 180).toFile(saveThumbFile);
					int end = getCurrentSeconds();
					log.debug("썸네일 하나 " + (end - start) + "초");
					totaltime += (end-start);
				}

				
				
				// dto에 넣어준다
				FileInfoDto fileInfoDto = new FileInfoDto();
				fileInfoDto.setFileName(files.get(i).getOriginalFilename());
				fileInfoDto.setFilePhyName(fileHardName);
				fileInfoDto.setFileThumbPhyName(fileThumbHardName);
				fileInfoDto.setFileSize(files.get(i).getSize());
				fileInfoDto.setFileContentType(contentType);
				fileInfoDto.setFileOrder(i);
				fileinfo.add(fileInfoDto);
			} else
			{
				throw new SqiException("이미지 또는 동영상만 업로드 가능합니다.");
			}
		}
		log.debug("모든 썸네일" + totaltime + "초");
		return fileinfo;
	}
    public int getCurrentSeconds(){
		return (int) (System.currentTimeMillis()/1000);
    }
}
