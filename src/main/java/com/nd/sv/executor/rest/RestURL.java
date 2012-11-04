package com.nd.sv.executor.rest;

import java.io.OutputStream;

/**
 */
public class RestURL {
    private String url;
    private int port;

    public RestURL(String url) {
        this(url, 80);
    }

    public RestURL(String url, int port) {
        this.url = url;
        this.port = port;
    }

    public void process(String xmlRequest, OutputStream outputStream) {

    }
}
