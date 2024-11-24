package com.taboola.api.utils;

import java.util.List;

public interface IJsonUtil {
    <T> String toJson(T object);

    <T> T fromJson(String json, Class<T> clazz);

    <T> List<T> fromJsonList(String json, Class<T> clazz);
}
