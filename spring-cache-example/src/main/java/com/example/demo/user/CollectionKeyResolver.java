package com.example.demo.user;

import java.lang.reflect.Method;
import org.springframework.cache.interceptor.KeyGenerator;

class CollectionKeyResolver implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return null;
    }
}
