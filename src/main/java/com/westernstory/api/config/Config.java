package com.westernstory.api.config;

/**
 * Created by fedor on 15/5/13.
 */
public class Config {

    public static final String WEB_KEY = "43869527";

    public static final String URL_ROOT = "http://120.26.115.144:8080/wsapi/"; // API根路径

    public static final String URL_STATIC = "http://120.26.115.144:808/"; // 图片根路径

    public static final String PATH_UPLOAD = "/data/static/upload"; // 图片上传路径（相对）

    public static final String PATH_EDITOR = "/data/static/editor"; // 编辑器上传路径（相对）

    public static final String URL_UPLOAD = URL_STATIC + "upload/"; // 图片上传路径（绝对）

    public static final String URL_EDITOR = URL_STATIC + "editor/"; // 图片编辑器路径（绝对）

}
