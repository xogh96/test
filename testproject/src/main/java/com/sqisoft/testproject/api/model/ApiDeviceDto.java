package com.sqisoft.testproject.api.model;

import javax.validation.constraints.NotEmpty;

import com.sqisoft.testproject.domain.DeviceEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "디바이스")
public class ApiDeviceDto
{
	@Getter
	@Setter
	public static class deviceFind
	{
		@NotEmpty
		@Schema(description = "디바이스_아이디", nullable = false)
		private Integer deviceSeq;

		public deviceFind(Integer seq)
		{
			this.deviceSeq = seq;
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class deviceInfo
	{
		@Schema(description = "디바이스_아이디")
		private Integer deviceSeq;

		@Schema(description = "디바이스_코드")
		private String deviceCode;

		@Schema(description = "디바이스_이름")
		private String deviceName;

		public deviceInfo(DeviceEntity deviceEntity)
		{
			deviceSeq = deviceEntity.getDeviceSeq();
			deviceCode = deviceEntity.getDeviceCode();
			deviceName = deviceEntity.getDeviceName();
		}
	}
}
