package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.User;
import guru.qa.niffler.jupiter.UsersQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.*;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.User.UserType.*;

@ExtendWith(UsersQueueExtension.class)
public class InviteTestAnnotationOnly {
    static {
        Configuration.browserSize = "1980x1024";
    }

    private final WelcomePage welcomePage = new WelcomePage();
    private final LoginPage loginPage = new LoginPage();
    private final MenuComponent menu = new MenuComponent();

    @BeforeEach
    void doLogin() {
        Selenide.open("http://127.0.0.1:3000/main");
    }

    @Test
    @DisplayName("Есть отправленный запрос")
    void pendingInvitationShouldExist(@User(INVITATION_SEND) UserJson user) {
        welcomePage.clickLoginButton();
        loginPage.login(user.username(), user.testData().password());
        PeoplePage peoplePage = menu.clickAllPeopleButton();
        peoplePage.pendingInvitationExists();
    }

    @Test
    @DisplayName("Есть входящий запрос")
    void incomingInvitationShouldExist(@User(INVITATION_RECEIVED) UserJson user) {
        welcomePage.clickLoginButton();
        loginPage.login(user.username(), user.testData().password());
        FriendsPage friendsPage = menu.clickFriendsButton();
        friendsPage.friendInvitationExists();
    }

    @Test
    @DisplayName("В списке друзей есть друг")
    void friendShouldExist(@User(WITH_FRIENDS) UserJson user) {
        welcomePage.clickLoginButton();
        loginPage.login(user.username(), user.testData().password());
        FriendsPage friendsPage = menu.clickFriendsButton();
        friendsPage.friendExists();
    }
}
