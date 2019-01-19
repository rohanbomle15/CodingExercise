package rohanbomle.codingexercise.api;

import retrofit2.Call;
import retrofit2.http.GET;
import rohanbomle.codingexercise.model.Album;

import java.util.List;

/***
 * Interface
 * USE TO : define all api calls
 */
public interface RetrofitApi {

    @GET("/albums")
    Call<List<Album>> getAlbums();
}
