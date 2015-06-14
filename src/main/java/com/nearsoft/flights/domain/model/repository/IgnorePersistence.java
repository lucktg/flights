package com.nearsoft.flights.domain.model.repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnorePersistence {
	public enum Operation{ UPDATE,INSERT,ALL}
	Operation ignore();
}
