package com.stone.nlp.config;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.corpus.io.IOUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

@SpringBootConfiguration
@ConfigurationProperties(prefix = "stone.nlp")
@Data
public class StoneNlpConfig {


    private String emotionModelPath;
    private String emotionModelFolder;
    private String classificationModelPath;
    private String classificationModelFolder;

    @Bean
    public IClassifier emotionClassifier() throws IOException {
        Resource resource = new ClassPathResource(emotionModelFolder);
        File file = resource.getFile();
        IClassifier classifier = new NaiveBayesClassifier();
        classifier.train(file.getAbsolutePath());
        return classifier;
    }

    @Bean
    public IClassifier classificationClassifier() throws IOException{
        Resource resource = new ClassPathResource(classificationModelFolder);
        File file = resource.getFile();
        IClassifier classifier = new NaiveBayesClassifier();
        classifier.train(file.getAbsolutePath());
        return classifier;
    }
}
