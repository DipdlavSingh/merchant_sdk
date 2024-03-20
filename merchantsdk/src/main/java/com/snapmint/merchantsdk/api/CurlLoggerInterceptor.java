package com.snapmint.merchantsdk.api;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class CurlLoggerInterceptor implements Interceptor {

    private final String tag;
    private final Charset utf8 = Charset.forName("UTF-8");

    public CurlLoggerInterceptor(String tag) {
        this.tag = tag;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        StringBuilder curlCommandBuilder = new StringBuilder("");
        // add cURL command
        curlCommandBuilder.append("cURL ");
        curlCommandBuilder.append("-X ");
        // add method
        curlCommandBuilder.append(request.method().toUpperCase() + " ");
        // adding headers
        for (String headerName : request.headers().names()) {
            addHeader(curlCommandBuilder, headerName, request.headers().get(headerName));
        }

        // adding request body
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            try {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = utf8;
                if (requestBody.contentType() != null) {
                    charset = requestBody.contentType().charset(charset);
                }
                addRequestBody(curlCommandBuilder, buffer.readString(charset));
            } catch (Exception e) {
                // Handle the exception as needed
                logError("Exception while reading request body: " + e.getMessage());
            }
        }

        // add request URL
        curlCommandBuilder.append(" \"" + request.url() + "\"");
        curlCommandBuilder.append(" -L");

        // Log the cURL command
        logCurlCommand(request.url().toString(), curlCommandBuilder.toString());
        return chain.proceed(request);
    }

    private void addHeader(StringBuilder curlCommandBuilder, String headerName, String headerValue) {
        curlCommandBuilder.append("-H \"" + headerName + ": " + headerValue + "\" ");
    }

    private void addRequestBody(StringBuilder curlCommandBuilder, String requestBody) {
        curlCommandBuilder.append(" --data-raw '" + requestBody + "'");
    }

    private void logCurlCommand(String url, String curlCommand) {
        // Log the cURL command (replace this with your preferred logging mechanism)
        printLog(tag, url, curlCommand);
    }

    @SuppressLint("LogNotTimber")
    private void printLog(String tag, String url, String msg) {
        // Setting tag if not null
        String logTag = tag != null ? tag : "CURL";

        // Log the message
        Log.d(logTag, "\n" + url + "\n" + SINGLE_DIVIDER + "\n" + msg + "\n" + SINGLE_DIVIDER + "\n");
    }

    @SuppressLint("LogNotTimber")
    private void logError(String message) {
        Log.e("CurlLoggerInterceptor", message);
    }

    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
}
