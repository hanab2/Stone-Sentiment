package com.stone.spider.formatter;

import org.apache.commons.lang3.time.DateUtils;
import us.codecraft.webmagic.model.formatter.ObjectFormatter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalDateTimeFormatter implements ObjectFormatter<LocalDateTime> {

    @Override
    public LocalDateTime format(String raw) throws Exception {
        if (raw == null){
            return LocalDateTime.now();
        }
        String pattern = "[0-9]{10}";
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(raw);
        if (matcher.find()) {
            String date = raw.substring(matcher.start(), matcher.end());
            long time = Long.parseLong(date);
            return LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.ofHours(8));
        }
        return LocalDateTime.now();

    }

    @Override
    public Class<LocalDateTime> clazz() {
        return LocalDateTime.class;
    }

    @Override
    public void initParam(String[] extra) {

    }
}
