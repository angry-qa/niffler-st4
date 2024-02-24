package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.db.model.CurrencyValues;
import guru.qa.niffler.jupiter.SpendJsonModel;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.Test;

public class HibernateSpendingTest extends BaseWebTest {

    WelcomePage welcomePage = new WelcomePage();
    LoginPage loginPage = new LoginPage();
    MainPage mainPage = new MainPage();

    static {
        Configuration.browserSize = "1980x1024";
    }

    @GenerateSpend(
            username = "duck",
            description = "QA.GURU Advanced 4",
            amount = 72500.00,
            category = "Курсы",
            currency = CurrencyValues.RUB
    )
    @Test
    void spendingTest(SpendJsonModel spend) {
        Selenide.open("http://127.0.0.1:3000");
        welcomePage
                .clickLoginButton();
        loginPage
                .setLogin("duck")
                .setPassword("duck")
                .submit();
        mainPage
                .checkSpendingsTableRowsHasSize(1)
                .selectSpendingByDescription(spend.description())
                .clickDeleteSelectedButton()
                .checkSpendingsTableRowsHasSize(0);
    }
}