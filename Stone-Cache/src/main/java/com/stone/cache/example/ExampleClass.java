package com.stone.cache.example;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ExampleClass implements Serializable {
    private static final long serialVersionUID = 315405805675155375L;
    private Long id;
    private String name;
    private LocalDateTime birth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirth() {
        return birth;
    }

    public void setBirth(LocalDateTime birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                '}';
    }
}
