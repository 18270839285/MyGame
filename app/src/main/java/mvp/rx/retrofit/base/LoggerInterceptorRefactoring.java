package mvp.rx.retrofit.base;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Description:
 * Data：2018/10/23-14:47
 * Author: lin
 */
public class LoggerInterceptorRefactoring implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        return logForResponse(request, response);
    }

    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json") || subtype.contains("xml") || subtype.contains("html")) //
                return true;
        }
        return false;
    }

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static Charset getCharset(MediaType contentType) {
        Charset charset = contentType != null ? contentType.charset(UTF8) : UTF8;
        if (charset == null) charset = UTF8;
        return charset;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        write(input, output);
        output.close();
        return output.toByteArray();
    }

    public static void write(InputStream inputStream, OutputStream outputStream) throws IOException {
        int len;
        byte[] buffer = new byte[4096];
        while ((len = inputStream.read(buffer)) != -1) outputStream.write(buffer, 0, len);
    }

    private Response logForResponse(Request request, Response response) {
        try {
            String url = request.url().toString();

            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            ResponseBody body = clone.body();
//            String resp = body.string();
            if (isPlaintext(body.contentType())) {
                byte[] bytes = toByteArray(body.byteStream());
                MediaType contentType = body.contentType();
                String body1 = new String(bytes, getCharset(contentType));
                Log.e("responseBody", " body1 = " + body1);
//                Logger.json(body);
                body = ResponseBody.create(body.contentType(), bytes);
            }

            Log.e("requestBody", url + "   " + bodyToString(request));
            Log.e("responseBody", body + " ");

//            MediaType mediaType = body.contentType();
//            body = ResponseBody.create(mediaType, resp);
            return response.newBuilder().body(body).build();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
