package uz.pdp.govqueue.aop;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", ignore = true)
@Mapping(target = "createdAt", expression = "java(System.currentTimeMillis())")
public @interface ToKetmon {
}
