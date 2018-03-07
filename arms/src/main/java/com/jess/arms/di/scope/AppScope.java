package com.jess.arms.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zwh
 * @date 2018\2\27 0027
 */

@Scope
@Retention(RUNTIME)
public @interface AppScope {
}
