package com.visionet.syh_mall.common.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Jackson序列化格式日期
 * 
 * @author xiaofb
 * @time 2017年9月8日
 */
public class JsonDateSerializer extends JsonSerializer<Date> {

	private final SimpleDateFormat datetimeFormat = new SimpleDateFormat(DateUtil.YMD_FULL);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.YMD1);

	@Override
	public void serialize(Date date, JsonGenerator gen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0
				&& cal.get(Calendar.SECOND) == 0) {
			gen.writeString(dateFormat.format(date));
		} else {
			gen.writeString(datetimeFormat.format(date));
		}

	}

}