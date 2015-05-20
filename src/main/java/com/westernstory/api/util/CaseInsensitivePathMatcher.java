package com.westernstory.api.util;

import org.springframework.util.AntPathMatcher;

import java.util.Map;

/**
 * Created by fedor on 15/3/27.
 */
public class CaseInsensitivePathMatcher extends AntPathMatcher {
    @Override
    protected boolean doMatch(String pattern, String path, boolean fullMatch, Map<String, String> uriTemplateVariables) {
        return super.doMatch(pattern.toLowerCase(), path.toLowerCase(), fullMatch, uriTemplateVariables);
    }
}
