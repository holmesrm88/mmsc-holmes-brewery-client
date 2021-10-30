package com.example.msscholmesbreweryclient.web.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Apache
 * Important to set up if you are going to have a lot of RESTful web service traffic.  Can greatly improve performance.
 */
@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

    private final Integer MAX_TOTAL_CONNECTIONS;
    private final Integer DEFAULT_MAX_TOTAL_CONNECTIONS_PER_ROUTE;
    private final Integer CONNECTION_REQUEST_TIMEOUT;
    private final Integer SOCKET_TIMEOUT;

    public BlockingRestTemplateCustomizer(@Value("${sfg.maxtotalconnections}") Integer MAX_TOTAL_CONNECTIONS,
                                          @Value("${sfg.defaultmaxtotalconnectionsperroute}")Integer DEFAULT_MAX_TOTAL_CONNECTIONS_PER_ROUTE,
                                          @Value("${sfg.connectionrequesttimeout}")Integer CONNECTION_REQUEST_TIMEOUT,
                                          @Value("${sfg.sockettimeout}")Integer SOCKET_TIMEOUT) {
        this.MAX_TOTAL_CONNECTIONS = MAX_TOTAL_CONNECTIONS;
        this.DEFAULT_MAX_TOTAL_CONNECTIONS_PER_ROUTE = DEFAULT_MAX_TOTAL_CONNECTIONS_PER_ROUTE;
        this.CONNECTION_REQUEST_TIMEOUT = CONNECTION_REQUEST_TIMEOUT;
        this.SOCKET_TIMEOUT = SOCKET_TIMEOUT;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_TOTAL_CONNECTIONS_PER_ROUTE);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }
}