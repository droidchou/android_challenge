package com.aetna.simplemailorder.api;

import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RequestInterceptor implements Interceptor {

    private static final String PRESCRIPTIONS_JSON_FILE_NAME = "prescriptions.json";

    private final AssetManager assetManager;

    public RequestInterceptor(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        InputStream is = assetManager.open(PRESCRIPTIONS_JSON_FILE_NAME);
        String json = new Scanner(is).useDelimiter("\\A").next();

        return new Response.Builder()
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .request(chain.request())
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("200 OK")
                .build();
    }
}
