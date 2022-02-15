package icu.yt.retrofitclient.retrofit;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import icu.yt.retrofitclient.model.Result;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author yt
 * @date 2022/2/15 13:58
 * 功能说明
 */
@RetrofitClient(baseUrl = "${remote.domain}")
public interface RetrofitCilent {


    @GET("/api/get")
    Result<String> apiGet();

    @FormUrlEncoded
    @POST("/api/post")
    Result<String> apiPost(@Field("text") String text);
}
