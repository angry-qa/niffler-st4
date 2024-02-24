package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.model.CategoryEntity;
import guru.qa.niffler.db.model.SpendEntity;
import java.util.Optional;

public interface SpendRepository {

    SpendEntity createSpend(SpendEntity spendEntity);

    CategoryEntity createCategory(CategoryEntity categoryEntity);

    Optional<CategoryEntity> findCategoryByUserIdAndCategory(String username, String category);
}
