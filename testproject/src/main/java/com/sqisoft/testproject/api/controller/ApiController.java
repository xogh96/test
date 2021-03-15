package com.sqisoft.testproject.api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqisoft.testproject.api.model.ApiCategoryDto;
import com.sqisoft.testproject.api.model.ApiContentDto;
import com.sqisoft.testproject.api.model.ApiDeviceDto;
import com.sqisoft.testproject.api.model.ApiMuseumDto;
import com.sqisoft.testproject.api.service.ApiCategoryService;
import com.sqisoft.testproject.api.service.ApiContentService;
import com.sqisoft.testproject.api.service.ApiDeviceService;
import com.sqisoft.testproject.api.service.ApiMuseumService;
import com.sqisoft.testproject.model.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "apis")
@RequestMapping("/v1/api")
@RestController
public class ApiController
{
	@Autowired
	private ApiDeviceService apiDeviceService;

	@Autowired
	private ApiMuseumService apiMuseumService;

	@Autowired
	private ApiCategoryService apiCategoryService;

	@Autowired
	private ApiContentService apiContentService;

	// 디바이스
	@PostMapping("/device")
	@Operation(summary = "디바이스 전체조회", description = "디바이스를 리스트로 조회", responses = {
					@ApiResponse(responseCode = "200", description = "디바이스 리스트 조회 성공", content = @Content(schema = @Schema(implementation = ApiDeviceDto.deviceInfo.class))),
					@ApiResponse(responseCode = "404", description = "리스트에 데이터가 없음", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> findAll()
	{
		List<ApiDeviceDto.deviceInfo> infolist = apiDeviceService.selectAll();
		if (infolist.size() == 0)
		{
			ErrorMessage e = new ErrorMessage("데이터가 존재하지않습니다 등록먼저 해주세요", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ApiDeviceDto.deviceInfo>>(infolist, HttpStatus.OK);
	}

	@PostMapping("/device/{deviceSeq}")
	@Operation(summary = "디바이스 조회", description = "아이디를 사용하여 디바이스 하나를 조회", responses = {
					@ApiResponse(responseCode = "200", description = "디바이스 조회 성공", content = @Content(schema = @Schema(implementation = ApiDeviceDto.deviceInfo.class))),
					@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "404", description = "아이디가 존재하지 않습니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> findOne(@PathVariable Integer deviceSeq)
	{
		ApiDeviceDto.deviceFind deviceDto = new ApiDeviceDto.deviceFind(deviceSeq);
		ApiDeviceDto.deviceInfo info = null;
		try
		{
			info = apiDeviceService.selectOne(deviceDto);
		} catch (NullPointerException exception)
		{
			ErrorMessage e = new ErrorMessage("아이디가 존재하지 않습니다", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ApiDeviceDto.deviceInfo>(info, HttpStatus.OK);
	}

	// 박물관
	@PostMapping("/museum")
	@Operation(summary = "박물관 전체조회", description = "호출 시 전체 박물관 리스트를 조회합니다", responses = {
					@ApiResponse(responseCode = "200", description = "박물관 조회 성공", content = @Content(schema = @Schema(implementation = ApiMuseumDto.museumInfo.class))),
					@ApiResponse(responseCode = "404", description = "리스트에 데이터가 없음", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> findAllMuseum()
	{
		List<ApiMuseumDto.museumInfo> infolist = apiMuseumService.selectAll();
		if (infolist.size() == 0)
		{
			ErrorMessage e = new ErrorMessage("데이터가 존재하지않습니다 등록먼저 해주세요", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ApiMuseumDto.museumInfo>>(infolist, HttpStatus.OK);
	}

	@PostMapping("/museum/{museumSeq}")
	@Operation(summary = "박물관 조회", description = "아이디를 사용하여 박물관을 조회합니다", responses = {
					@ApiResponse(responseCode = "200", description = "박물관 조회 성공", content = @Content(schema = @Schema(implementation = ApiMuseumDto.museumInfo.class))),
					@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "404", description = "존재하지않는 아이디", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> findOneMuseum(
					@Parameter(name = "museumSeq", description = "박물관의 id", in = ParameterIn.PATH) @PathVariable("museumSeq") Integer museumSeq)
	{
		ApiMuseumDto.museumFind museumDto = new ApiMuseumDto.museumFind(museumSeq);
		ApiMuseumDto.museumInfo info = null;
		try
		{
			info = apiMuseumService.selectOne(museumDto);
		} catch (NullPointerException exception)
		{
			ErrorMessage e = new ErrorMessage("아이디가 존재하지 않습니다", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ApiMuseumDto.museumInfo>(info, HttpStatus.OK);
	}

	// 카테고리
	@PostMapping("/category")
	@Operation(summary = "카테고리 전체조회", description = "호출 시 전체 카테고리 리스트를 조회합니다", responses = {
					@ApiResponse(responseCode = "200", description = "카테고리 조회 성공", content = @Content(schema = @Schema(implementation = ApiCategoryDto.categoryInfo.class))),
					@ApiResponse(responseCode = "404", description = "리스트에 데이터가 없음", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> findAllcate()
	{
		List<ApiCategoryDto.categoryInfo> infolist = apiCategoryService.selectAll();
		if (infolist.size() == 0)
		{
			ErrorMessage e = new ErrorMessage("데이터가 존재하지않습니다 등록먼저 해주세요", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ApiCategoryDto.categoryInfo>>(infolist, HttpStatus.OK);
	}

	@PostMapping("/category/{categorySeq}")
	@Operation(summary = "카테고리 조회", description = "아이디를 사용하여 카테고리를 조회합니다", responses = {
					@ApiResponse(responseCode = "200", description = "카테고리 조회 성공", content = @Content(schema = @Schema(implementation = ApiCategoryDto.categoryInfo.class))),
					@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "404", description = "존재하지않는 아이디", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> findOnecate(
					@Parameter(name = "categorySeq", description = "카테고리 id", in = ParameterIn.PATH) @PathVariable("categorySeq") Integer categorySeq)
	{
		ApiCategoryDto.categoryFind categoryDto = new ApiCategoryDto.categoryFind(categorySeq);
		ApiCategoryDto.categoryInfo info = null;
		try
		{
			info = apiCategoryService.selectOne(categoryDto);
		} catch (NullPointerException exception)
		{
			ErrorMessage e = new ErrorMessage("아이디가 존재하지 않습니다", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ApiCategoryDto.categoryInfo>(info, HttpStatus.OK);
	}

	// 콘텐츠
	@PostMapping("/content")
	@Operation(summary = "콘텐츠 전체조회", description = "호출 시 전체 콘텐츠 리스트를 조회합니다", responses = {
					@ApiResponse(responseCode = "200", description = "콘텐츠 조회 성공", content = @Content(schema = @Schema(implementation = ApiContentDto.contentInfo.class))),
					@ApiResponse(responseCode = "404", description = "리스트에 데이터가 없음", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> findAllcont()
	{
		List<ApiContentDto.contentInfo> infolist = apiContentService.selectAll();
		if (infolist.size() == 0)
		{
			ErrorMessage e = new ErrorMessage("데이터가 존재하지않습니다 등록먼저 해주세요", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ApiContentDto.contentInfo>>(infolist, HttpStatus.OK);
	}

	@PostMapping("/content/{contentSeq}")
	@Operation(summary = "콘텐츠 조회", description = "아이디를 사용하여 콘텐츠를 조회합니다", responses = {
					@ApiResponse(responseCode = "200", description = "콘텐츠 조회 성공", content = @Content(schema = @Schema(implementation = ApiContentDto.contentInfo.class))),
					@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "404", description = "존재하지않는 아이디", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> findOnecont(
					@Parameter(name = "contentSeq", description = "콘텐츠 id", in = ParameterIn.PATH) @PathVariable("contentSeq") Integer contentSeq)
	{
		ApiContentDto.contentFind contentDto = new ApiContentDto.contentFind(contentSeq);
		ApiContentDto.contentInfo info = null;
		try
		{
			info = apiContentService.selectOne(contentDto);
		} catch (NullPointerException exception)
		{
			ErrorMessage e = new ErrorMessage("아이디가 존재하지 않습니다", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ApiContentDto.contentInfo>(info, HttpStatus.OK);
	}

	@GetMapping("/content/download/{contentSeq}")
	@Operation(summary = "콘텐츠 파일 다운로드", description = "아이디를 사용하여 콘텐츠를 다운로드 합니다", responses = {
					@ApiResponse(responseCode = "200", description = "다운로드 성공"),
					@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "404", description = "존재하지않는 아이디", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> download(
					@Parameter(name = "contentSeq", description = "콘텐츠 id", in = ParameterIn.PATH) @PathVariable("contentSeq") Integer contentSeq)
					throws IOException
	{
		ApiContentDto.contentFind contentDto = new ApiContentDto.contentFind(contentSeq);
		ResponseEntity<Resource> data = null;
		try
		{
			data = apiContentService.downloadOne(contentDto);
		} catch (NullPointerException exception)
		{
			ErrorMessage e = new ErrorMessage("아이디가 존재하지 않습니다", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}
		return data;
	}
}
