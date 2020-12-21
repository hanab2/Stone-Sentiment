package com.stone.spider.formatter;

import org.apache.commons.lang3.time.DateUtils;
import us.codecraft.webmagic.model.formatter.ObjectFormatter;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeFormatter implements ObjectFormatter<LocalDateTime> {

    public static final String[] DEFAULT_PATTERN = new String[]{"yyyy-MM-dd HH:mm"};
    private String[] datePatterns = DEFAULT_PATTERN;

    @Override
    public LocalDateTime format(String raw) throws Exception {
        return DateUtils.parseDate(raw, datePatterns)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @Override
    public Class<LocalDateTime> clazz() {
        return LocalDateTime.class;
    }

    @Override
    public void initParam(String[] extra) {
        if (extra != null && !(extra.length == 1 && extra[0].length() == 0)) {
            datePatterns = extra;
        }
    }
}
