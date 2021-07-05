package myproject.nyt.util;

import myproject.nyt.interfaces.RestApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;

    public static RestApiService getApiService(String api_host) {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(api_host)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(RestApiService.class);
    }
}

