package guru.qa.niffler.jupiter;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.niffler.db.model.CurrencyValues;

import java.util.Date;
import java.util.UUID;

public record SpendJsonModel(
    @JsonProperty("id")
    UUID id,
    @JsonProperty("spendDate")
    Date spendDate,
    @JsonProperty("category")
    String category,
    @JsonProperty("currency")
    CurrencyValues currency,
    @JsonProperty("amount")
    Double amount,
    @JsonProperty("description")
    String description,
    @JsonProperty("username")
    String username) {
}
