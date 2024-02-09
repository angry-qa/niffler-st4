package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage> {

  private final SelenideElement usernameInput = $("input[name='username']");
  private final SelenideElement passwordInput = $("input[name='password']");
  private final SelenideElement signInButton = $("button[type='submit']");

  @Step("В поле username ввести [{username}]")
  public LoginPage setLogin(String username) {
    usernameInput.setValue(username);
    return this;
  }

  @Step("В поле password ввести [{password}]")
  public LoginPage setPassword(String password) {
    passwordInput.setValue(password);
    return this;
  }

  @Step("Нажать кнопку [Sign in]")
  public void submit() {
    signInButton.click();
  }

  @Step("Залогиниться")
  public void login(String username, String password) {
    setLogin(username);
    setPassword(password);
    submit();
  }
}
