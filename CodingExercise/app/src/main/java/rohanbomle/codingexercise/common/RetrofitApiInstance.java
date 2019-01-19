package rohanbomle.codingexercise.common;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/***
 * Common RETROFIT singleTon class
 * USE TO : provide Retrofit instance to all the modules in app
 */
public class RetrofitApiInstance {

    private static Retrofit mRetrofit;
    private static final String mBaseUrl = "https://jsonplaceholder.typicode.com/";

    public static Retrofit getRetrofitInstance() {
        if (mRetrofit == null) {
            mRetrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(mBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
