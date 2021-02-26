package com.sqisoft.testproject.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SqiUtils
{
	private SqiUtils()
	{
		throw new IllegalStateException("SqiUtils class");
	}

	public static String getYYYYmm()
	{
		Calendar c1 = Calendar.getInstance();
		return (new SimpleDateFormat("yyyyMM")).format(c1.getTime());
	}

}
