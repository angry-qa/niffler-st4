package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.CategoryApi;
import guru.qa.niffler.api.SpendApi;
import guru.qa.niffler.jupiter.CategoryJson;
import guru.qa.niffler.jupiter.SpendJsonModel;
import guru.qa.niffler.jupiter.dto.CategoryDto;
import guru.qa.niffler.jupiter.dto.SpendDto;
import java.io.IOException;
import java.util.Optional;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RestSpendExtensionDto extends SpendExtensionAbstract {

    private static final OkHttpClient httpClient = new OkHttpClient.Builder().build();
    private static final Retrofit retrofit = new Retrofit.Builder()
            .client(httpClient)
            .baseUrl("http://127.0.0.1:8093")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private final SpendApi spendApi = retrofit.create(SpendApi.class);
    private final CategoryApi categoryApi = retrofit.create(CategoryApi.class);

    @Override
    protected SpendDto createSpend(SpendDto spendDto) {
        SpendJsonModel spendJsonToCreate = spendDtoToJson(spendDto);
        SpendJsonModel created = null;
        try {
            created = spendApi.addSpend(spendJsonToCreate).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoryJsonToDto(created);
    }

    @Override
    protected CategoryDto createCategory(CategoryDto categoryDto) {
        try {
            Optional<CategoryJson> categoryCreatedYet = categoryApi
                    .getCategories(categoryDto.userName())
                    .execute()
                    .body()
                    .stream()
                    .filter(userCategory -> userCategory.category().equals(categoryDto.category()))
                    .findFirst();

            if (categoryCreatedYet.isPresent()) {
                CategoryJson categoryCreatedYetForDto = categoryCreatedYet.get();
                return new CategoryDto(categoryCreatedYetForDto.id(),
                        categoryCreatedYetForDto.username(),
                        categoryCreatedYetForDto.category());
            }

            CategoryJson categoryForCreation = categoryDtoToJson(categoryDto);
            Response<CategoryJson> categoryJsonResponse = categoryApi.addCategory(categoryForCreation)
                    .execute();

            if (categoryJsonResponse.isSuccessful()) {
                return categoryJsonToDto(categoryJsonResponse.body());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CategoryJson categoryDtoToJson(CategoryDto categoryDto) {
        return new CategoryJson(categoryDto.id(), categoryDto.category(), categoryDto.userName());
    }

    private CategoryDto categoryJsonToDto(CategoryJson categoryJson) {
        return new CategoryDto(categoryJson.id(), categoryJson.username(), categoryJson.category());
    }

    private SpendJsonModel spendDtoToJson(SpendDto spendDto) {
        return new SpendJsonModel(spendDto.id(),
                spendDto.spendDate(),
                spendDto.category().category(),
                spendDto.currency(),
                spendDto.amount(),
                spendDto.description(),
                spendDto.username());
    }

    private SpendDto categoryJsonToDto(SpendJsonModel spendJson) {
        return new SpendDto(spendJson.id(),
                spendJson.username(),
                spendJson.currency(),
                spendJson.spendDate(),
                spendJson.amount(),
                spendJson.description(),
                new CategoryDto(null, spendJson.username(), spendJson.category()));
    }
}
