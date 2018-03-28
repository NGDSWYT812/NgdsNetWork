package ngds.app.retrofit;

import cn.com.ngds.lib.network.call.NgdsCall;
import cn.com.ngds.lib.network.type.Response;
import retrofit2.http.GET;

/**
 * Created by wangyt on 2018/2/8.
 * : description
 */

public interface TestApi {
    @GET("gamestore/channel_guide/gf")
    NgdsCall<Response<String>> Test();

    //推荐游戏
    @GET("games/hot")
    NgdsCall<Response<String>> Test2();
}
