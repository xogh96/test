package com.sqisoft.testproject.apis.model;

import com.sqisoft.testproject.domain.DeviceEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ApiDeviceDto
{
	@Getter
	@Setter
	public static class find
	{
		private Integer deviceSeq;
	}

	@Getter
	@Setter
	public static class save
	{
		private Integer deviceSeq;

		private String deviceCode;

		private String deviceName;
	}

	@Getter
	@Setter
	public static class delete
	{
		private Integer deviceSeq;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class info
	{
		private Integer deviceSeq;

		private String deviceCode;

		private String deviceName;

		public info(DeviceEntity deviceEntity)
		{
			deviceSeq = deviceEntity.getDeviceSeq();
			deviceCode = deviceEntity.getDeviceCode();
			deviceName = deviceEntity.getDeviceName();
		}
	}
}
