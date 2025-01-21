package com.qcc.hallow.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Collections;

@Component
@PropertySource("classpath:application.properties")
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";
    private final String EXPECTED_API_KEY; // Replace with your API key

    // Inject the Environment and retrieve the API key
    public ApiKeyFilter(Environment environment) {
        this.EXPECTED_API_KEY = environment.getProperty("application.api.key");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader(API_KEY_HEADER);
        String path = request.getRequestURI();
        System.out.println("Incoming Headers:");
        Collections.list(request.getHeaderNames())
                .forEach(header -> System.out.println(header + ": " + request.getHeader(header)));

        // Exclude Swagger UI and related resources
        if (path.startsWith("/hallow/swagger-ui")
                || path.startsWith("/hallow/api-docs")
                || path.startsWith("/hallow/v3/api-docs")
                || path.startsWith("/hallow/swagger-resources")
                || path.startsWith("/hallow/webjars")) {
            filterChain.doFilter(request, response);
            System.out.println("Request proceeded successfully."); // Debugging line
            return;
        }

        if (EXPECTED_API_KEY.equals(apiKey)) {
            // Allow request to proceed
            filterChain.doFilter(request, response);
            System.out.println("Request proceeded successfully."); // Debugging line
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Forbidden: Invalid API Key");
        }
    }
}
