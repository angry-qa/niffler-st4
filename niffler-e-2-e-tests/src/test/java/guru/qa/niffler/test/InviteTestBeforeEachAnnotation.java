package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.UsersQueueExtension;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.WelcomePage;
import guru.qa.niffler.pages.MenuComponent;
import guru.qa.niffler.pages.PeoplePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.User.UserType.*;

@ExtendWith(UsersQueueExtension.class)
public class InviteTestBeforeEachAnnotation {
    static {
        Configuration.browserSize = "1980x1024";
    }

    private final WelcomePage welcomePage = new WelcomePage();
    private final LoginPage loginPage = new LoginPage();

    @BeforeEach
    void doLogin(@User(INVITATION_SEND) UserJson user) {
        Selenide.open("http://127.0.0.1:3000/main");
        welcomePage.clickLoginButton();
        loginPage.login(user.username(), user.testData().password());
    }

    @Test
    @DisplayName("Есть отправленный запрос")
    void pendingInvitation() {
        MenuComponent menu = new MenuComponent();
        PeoplePage peoplePage = menu.clickAllPeopleButton();
        peoplePage.pendingInvitationExists();
    }
}
