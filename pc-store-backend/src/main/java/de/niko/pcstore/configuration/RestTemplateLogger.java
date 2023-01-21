package de.niko.pcstore.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

@Slf4j
@Configuration
public class RestTemplateLogger implements ClientHttpRequestInterceptor {
    private static final String RTLM00001E = "RTLM00001E API Request per rest error " +
            "[Request: URI='{}', Method='{}', Headers='{}', Body='{}']" +
            "[Response: Code='{}', Text='{}', Headers='{}', Body='{}']";
    private static final String RTLM00001I = "RTLM00001I API Request per rest info " +
            "[Request: URI='{}', Method='{}', Headers='{}', Body='{}']" +
            "[Response: Code='{}', Text='{}', Headers='{}', Body='{}']";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] requestBodyBytes, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders requestHeaders = request.getHeaders();
        String requestMethod = request.getMethodValue();
        URI requestURI = request.getURI();
        String requestBody = new String(requestBodyBytes, Charset.defaultCharset());

        ClientHttpResponse response = execution.execute(request, requestBodyBytes);

        HttpStatus responseStatusCode = response.getStatusCode();
        String responseStatusText = response.getStatusText();
        HttpHeaders responseHeaders = response.getHeaders();
        InputStream responseBody = response.getBody();
        String responseBodyAsString = StreamUtils.copyToString(responseBody, Charset.defaultCharset());

        if (responseStatusCode.isError()) {
            log.error(RTLM00001E, requestURI, requestMethod, requestHeaders, requestBody, responseStatusCode, responseStatusText, responseHeaders, responseBodyAsString);
        } else {
            log.info(RTLM00001I, requestURI, requestMethod, requestHeaders, requestBody, responseStatusCode, responseStatusText, responseHeaders, responseBodyAsString);
        }

        return response;
    }
}
