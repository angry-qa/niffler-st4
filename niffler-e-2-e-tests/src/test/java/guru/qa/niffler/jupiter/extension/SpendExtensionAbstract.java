package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.SpendJsonModel;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.dto.CategoryDto;
import guru.qa.niffler.jupiter.dto.SpendDto;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

public abstract class SpendExtensionAbstract implements BeforeEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(SpendExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        Optional<GenerateSpend> spend = AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                GenerateSpend.class
        );

        if (spend.isPresent()) {
            GenerateSpend generateSpend = spend.get();

            CategoryDto createdCategory = createCategory(
                    new CategoryDto(null,
                            generateSpend.username(),
                            generateSpend.category()));

            SpendDto spendCreated = createSpend(
                    new SpendDto(null,
                            generateSpend.username(),
                            generateSpend.currency(),
                            convertLocalDateToDate(LocalDate.now()),
                            generateSpend.amount(),
                            generateSpend.description(),
                            createdCategory)
            );

            extensionContext.getStore(NAMESPACE)
                    .put(extensionContext.getUniqueId(), spendDtoToJson(spendCreated));
        }
    }

    private Date convertLocalDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
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

    protected abstract SpendDto createSpend(SpendDto spendDto);

    protected abstract CategoryDto createCategory(CategoryDto categoryDto);
}
