package com.simonc312.androidapiexercise;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated classes will made open for mocking during tests
 * Created by simonchen on 6/1/17.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface KotlinTestOpen {
}
