package guru.qa.niffler.api;

import java.util.List;

import guru.qa.niffler.jupiter.CategoryJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CategoryApi {

  @POST("/category")
  Call<CategoryJson> addCategory(@Body CategoryJson category);

  @GET("/categories")
  Call<List<CategoryJson>> getCategories (@Query("username") String username);
}
