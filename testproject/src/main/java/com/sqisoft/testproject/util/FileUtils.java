package com.sqisoft.testproject.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileUtils
{
	public String getContentType(File f) throws IOException
	{
		return new org.apache.tika.Tika().detect(f);
	}

	public String getContentType(InputStream is) throws IOException
	{
		return new org.apache.tika.Tika().detect(is);
	}
}
