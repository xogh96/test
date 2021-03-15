package com.sqisoft.testproject;

import org.junit.jupiter.api.Test;


public class FfmpegTest
{
	
	@Test
	public void getId()
	{
		String ffmpegpath = "D:\\d_temp\\ffmpeg\\ffmpeg.exe";
		System.out.println(ffmpegpath);
		Runtime run = Runtime.getRuntime();
		String filepath = "D:\\d_temp\\thpark\\";
		String filename = "0b350dbc-4e7b-40c4-9616-e5128e6ee878.mp4";
		String realfilename = "0b350dbc-4e7b-40c4-9616-e5128e6ee878";
		String vd = String.format("%s -i \"%s\" -r 2 -s 200x180 \"%s.gif\"", ffmpegpath , filepath + filename , filepath + realfilename );
		try {
			run.exec("cmd.exe chcp 65001");
			System.out.println(vd);
			run.exec(vd);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
