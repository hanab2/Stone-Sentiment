package com.stone.nlp.hanlp.executor;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractHanExecutor implements HanExecutor{

    private CompletableFuture<String> future;

    //ublic abstract String execute(Map<String, Object> params);
    
}
