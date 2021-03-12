package com.sqisoft.testproject.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqisoft.testproject.api.model.ApiDeviceDto;
import com.sqisoft.testproject.api.model.ApiMuseumDto;
import com.sqisoft.testproject.api.service.ApiDeviceService;
import com.sqisoft.testproject.model.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "device", description = "디바이스 관련 api입니다")
@Slf4j
@RequestMapping("/api/device")
@RestController
public class ApiDeviceController
{
	@Autowired
	private ApiDeviceService apiDeviceService;

	@PostMapping("")
	@Operation(summary = "디바이스 SelectAll", description = "호출 시 디바이스와 관련된 정보를 리스트로 조회합니다", responses = {
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

	@PostMapping("/{deviceSeq}")
	@Operation(summary = "디바이스 SelectOne", description = "아이디를 사용하여 디바이스를 조회합니다", responses = {
					@ApiResponse(responseCode = "200", description = "박물관 조회 성공", content = @Content(schema = @Schema(implementation = ApiDeviceDto.deviceInfo.class))),
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

	@PostMapping("/add")
	@Operation(summary = "디바이스 Create", description = "호출 시 디바이스 하나를 등록합니다", responses = {
					@ApiResponse(responseCode = "200", description = "등록완료", content = @Content(schema = @Schema(implementation = ApiDeviceDto.deviceInfo.class))),
					@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> add(@Valid @RequestBody ApiDeviceDto.deviceSave deviceDto)
	{
		log.debug(deviceDto.toString());
		ApiDeviceDto.deviceInfo info = null;
		info = apiDeviceService.insertOne(deviceDto);
		return new ResponseEntity<ApiDeviceDto.deviceInfo>(info, HttpStatus.OK);
	}

	@PostMapping("/edit/{deviceSeq}")
	@Operation(summary = "디바이스 Update", description = "아이디를 사용하여 아이디에 맞는 device를 update합니다 (입력한 값만 바뀌고 입력하지 않은 값은 원래 데이터를 반환) ", responses = {
					@ApiResponse(responseCode = "200", description = "등록완료", content = @Content(schema = @Schema(implementation = ApiMuseumDto.museumUpdate.class))),
					@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "404", description = "디바이스 아이디가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "내부서버 오류입니다", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	public ResponseEntity<?> update(ApiDeviceDto.deviceUpdate deviceDto,
					@Parameter(name = "deviceSeq", description = "디바이스 id", in = ParameterIn.PATH) @PathVariable("deviceSeq") Integer deviceSeq)
	{
		deviceDto.setDeviceSeq(deviceSeq);
		ApiDeviceDto.deviceInfo info = null;
		try
		{
			info = apiDeviceService.updateOne(deviceDto);
		} catch (NullPointerException d)
		{
			ErrorMessage e = new ErrorMessage("디바이스 아이디가 존재하지 않습니다", "404");
			return new ResponseEntity<ErrorMessage>(e, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ApiDeviceDto.deviceInfo>(info, HttpStatus.OK);
	}

	@PostMapping("/remove/{deviceSeq}")
	public void del(ApiDeviceDto.deviceDelete deviceDto)
	{
		apiDeviceService.deleteOne(deviceDto);
	}
}
