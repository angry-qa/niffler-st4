package guru.qa.niffler.api;

import java.util.Date;
import java.util.List;

import guru.qa.niffler.db.model.CurrencyValues;
import guru.qa.niffler.jupiter.SpendJsonModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SpendApi {

  @POST("/addSpend")
  Call<SpendJsonModel> addSpend(@Body SpendJsonModel spend);

  @GET("/spends")
  Call<List<SpendJsonModel>> getSpends(@Query("username") String username,
                                  @Query("filterCurrency") CurrencyValues filterCurrency,
                                  @Query("from") Date from,
                                  @Query("to") Date to);

  @PATCH("/editSpend")
  Call<SpendJsonModel> editSpend(@Body SpendJsonModel spend);

  @DELETE("/deleteSpends")
  void deleteSpends(@Query("username") String username,
                    @Query("ids") List<String> ids);
}
