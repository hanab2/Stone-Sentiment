package com.stone.spider.model;

import cn.hutool.http.HtmlUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.stone.spider.formatter.LocalDateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.Formatter;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "sentiment_news_test")
//@TargetUrl(value = {"https://www.thepaper.cn/newsDetail_forward_[0-9]{8}"})
//@HelpUrl(value = {"https://www.thepaper.cn/channel_[0-9]{5}"})
@HelpUrl(value = {"https://www.sohu.com/c/8/[0-9]{4}"})
@TargetUrl(value = {"https://www.sohu.com/a/[0-9]{9}_[0-9]{6}"})
public class RawNews implements Serializable, AfterExtractor {
    private static final long serialVersionUID = 551308210904842205L;
    @Id
    private Long newsId;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    @ExtractBy(value = "//*[@id=\"article-container\"]/div[2]/div[1]/div/div[1]/h1", type = ExtractBy.Type.XPath, source = ExtractBy.Source.RawText)
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    @ExtractBy(value = "//*[@id=\"mp-editor\"]", type = ExtractBy.Type.XPath)
    private String body;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String summary;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String emotion;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String referencePath;
    @Field(type = FieldType.Auto, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime embodyTime;
    //    @Field(type = FieldType.Auto, pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
//    @ExtractBy(value = "//*[@id=\"news-time\"]",type = ExtractBy.Type.XPath)
//    @Formatter(formatter = LocalDateTimeFormatter.class)
//    private LocalDateTime publishedTime;
    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String tag;
    @Field(type = FieldType.Integer)
    private Integer status;

    @Override
    public void afterProcess(Page page) {
        this.body = HtmlUtil.removeHtmlAttr(this.body, "class");
        this.status = 0;
        this.referencePath = page.getUrl().toString();
        this.embodyTime = LocalDateTime.now();
    }
}
