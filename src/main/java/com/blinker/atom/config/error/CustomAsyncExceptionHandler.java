package com.blinker.atom.config.error;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(@NotNull Throwable throwable, @NotNull Method method, Object... obj) {
        StringBuilder paramValues = new StringBuilder();
        for (Object param : obj) {
            paramValues.append(param.toString()).append(" ");
        }
        log.error("Exception message: {}\nMethod name: {}\nParam Values : {}", throwable.getMessage(), method.getName(), paramValues, throwable);
    }
}
