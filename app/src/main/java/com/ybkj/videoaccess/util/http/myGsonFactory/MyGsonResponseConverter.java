package com.ybkj.videoaccess.util.http.myGsonFactory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.ybkj.videoaccess.app.ConstantSys;
import com.ybkj.videoaccess.util.http.HttpErrorException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 自定义Gosn转换器--数据返回Gson解析
 *
 * @author Created by CH L on 2017/11/10.
 */

public class MyGsonResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public MyGsonResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        int status = ConstantSys.HttpStatus.STATUS_UNKNOWN_ERROR;
        try {
            String body = value.string();
            JSONObject json = new JSONObject(body);
            status = json.optInt("status");
            String msg = json.optString("msg", "");

            if (status == 0) {
                return adapter.fromJson(body);
            } else {
                throw new HttpErrorException(status, msg);
            }
        } catch (Exception e) {
            throw new HttpErrorException(status, e.getMessage());
        } finally {
            value.close();
        }
    }
}
