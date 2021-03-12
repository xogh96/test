package com.sqisoft.testproject.api.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.sqisoft.testproject.domain.DeviceEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "디바이스")
public class ApiDeviceDto
{
	@Getter
	@Setter
	public static class deviceFind
	{
		@NotEmpty
		@Schema(description = "디바이스_아이디" , nullable = false)
		private Integer deviceSeq;
		
		public deviceFind(Integer seq) {
			this.deviceSeq = seq;
		}
	}

	@Getter
	@Setter
	@ToString
	public static class deviceSave
	{
		@NotEmpty
		@Schema(description = "디바이스_코드" , nullable = false)
		private String deviceCode;
		
		@NotEmpty
		@Schema(description = "디바이스_이름" , nullable = false)
		private String deviceName;
	}

	@Getter
	@Setter
	public static class deviceUpdate
	{
		@NotEmpty
		@Schema(description = "디바이스_아이디" , nullable = false , hidden = true)
		private Integer deviceSeq;

		@Schema(description = "디바이스_이름")
		private String deviceName;
		
		public deviceUpdate(Integer seq) {
			this.deviceSeq = seq;
		}
	}

	@Getter
	@Setter
	public static class deviceDelete
	{
		@NotEmpty
		@Schema(description = "디바이스_아이디")
		private Integer deviceSeq;
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
