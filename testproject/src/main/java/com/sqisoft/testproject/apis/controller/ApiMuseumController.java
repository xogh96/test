package com.sqisoft.testproject.apis.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.sqisoft.testproject.apis.model.ApiMuseumDto;
import com.sqisoft.testproject.apis.service.ApiMuseumService;
import com.sqisoft.testproject.model.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "museum", description = "박물관 관련 api입니다")
@Slf4j
@RequestMapping("/api/museum")
@RestController
public class ApiMuseumController
{
	@Autowired
	private ApiMuseumService apiMuseumService;

	@PostMapping("")
	@Operation(summary = "박물관 SelectAll", description = "호출 시 전체 박물관 리스트를 조회합니다", responses = {
					@ApiResponse(responseCode = "200", description = "박물관 조회 성공", content = @Content(schema = @Schema(implementation = ApiMuseumDto.museumInfo.class))),
					@ApiResponse(responseCode = "404", description = "리스트에 데이터가 없음", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> findAll()
	{
		List<ApiMuseumDto.museumInfo> infolist = apiMuseumService.selectAll();
		if (infolist.size() == 0)
		{
			ErrorMessage e = new ErrorMessage("데이터가 존재하지않습니다 등록먼저 해주세요", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ApiMuseumDto.museumInfo>>(infolist, HttpStatus.OK);
	}

	@PostMapping("/{museumSeq}")
	@Operation(summary = "박물관 SelectOne", description = "아이디를 사용하여 박물관 리스트를 조회합니다", responses = {
					@ApiResponse(responseCode = "200", description = "박물관 조회 성공", content = @Content(schema = @Schema(implementation = ApiMuseumDto.museumInfo.class))),
					@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "404", description = "존재하지않는 아이디", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> findOne(
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

	@PostMapping(value = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "박물관 Create", description = "호출 시 박물관하나를 등록합니다", responses = {
					@ApiResponse(responseCode = "200", description = "등록완료", content = @Content(schema = @Schema(implementation = ApiMuseumDto.museumSave.class))),
					@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "404", description = "디바이스 아이디가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> add(ApiMuseumDto.museumSave museumDto) throws IOException
	{
		log.debug(museumDto.toString());
		ApiMuseumDto.museumInfo info = null;
		try
		{
			info = apiMuseumService.insertOne(museumDto);
		} catch (NullPointerException d)
		{
			ErrorMessage e = new ErrorMessage("디바이스 아이디가 존재하지 않습니다", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		} catch(HttpMessageNotReadableException rd) {
			ErrorMessage e = new ErrorMessage("json형식이 잘못됐습니다", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ApiMuseumDto.museumInfo>(info, HttpStatus.OK);
	}

	@PostMapping(value = "/edit/{museumSeq}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "박물관 Update", description = "아이디를 사용하여 아이디에 맞는 museum을 update합니다 (입력한 값만 바뀌고 입력하지 않은 값은 원래 데이터를 반환) ", responses = {
					@ApiResponse(responseCode = "200", description = "등록완료", content = @Content(schema = @Schema(implementation = ApiMuseumDto.museumUpdate.class))),
					@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "404", description = "박물관 아이디가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> add(ApiMuseumDto.museumUpdate museumDto,
					@Parameter(name = "museumSeq", description = "박물관의 id", in = ParameterIn.PATH) @PathVariable("museumSeq") Integer museumSeq)
					throws IOException
	{
		museumDto.setMuseumSeq(museumSeq);
		ApiMuseumDto.museumInfo info = null;
		try
		{
			info = apiMuseumService.updateOne(museumDto);
		} catch (NullPointerException d)
		{
			ErrorMessage e = new ErrorMessage("박물관 아이디 또는 디바이스 아이디가 존재하지 않습니다", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ApiMuseumDto.museumInfo>(info, HttpStatus.OK);
	}

	@PostMapping("/remove/{museumSeq}")
	@Operation(summary = "박물관 Delete", description = "아이디를 사용하여 museum을 삭제합니다", responses = {
					@ApiResponse(responseCode = "200", description = "삭제완료"),
					@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "404", description = "아이디가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> del(
					@Parameter(name = "museumSeq", description = "박물관 id", in = ParameterIn.PATH) @PathVariable Integer museumSeq)
	{
		ApiMuseumDto.museumDelete museumDto = new ApiMuseumDto.museumDelete(museumSeq);
		try
		{
			apiMuseumService.deleteOne(museumDto);
		} catch (EmptyResultDataAccessException ne)
		{
			ErrorMessage e = new ErrorMessage("박물관 아이디가 존재하지않아 삭제할 수 없습니다", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
