package com.wani.gym.security.filters;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class FilterSkipMatcher implements RequestMatcher {

    private OrRequestMatcher orRequestMatcher;
    private RequestMatcher processingMatcher;

    public FilterSkipMatcher(List<String> pathToSkip, String processingPath) {
        this.orRequestMatcher = new OrRequestMatcher(getAntPathList(pathToSkip));
        this.processingMatcher = new AntPathRequestMatcher(processingPath);
    }

    private List<RequestMatcher> getAntPathList(List<String> pathToSkip) {
        return pathToSkip.stream()
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toList());
    }
    @Override
    public boolean matches(HttpServletRequest req) {
        return !orRequestMatcher.matches(req) && processingMatcher.matches(req);
    }
}
