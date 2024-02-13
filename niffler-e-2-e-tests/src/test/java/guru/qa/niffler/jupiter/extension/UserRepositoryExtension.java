package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.db.repository.UserRepository;
import guru.qa.niffler.db.repository.UserRepositoryJdbc;
import guru.qa.niffler.db.repository.UserRepositorySJdbc;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;

public class UserRepositoryExtension implements TestInstancePostProcessor {
  @Override
  public void postProcessTestInstance(Object o, ExtensionContext extensionContext) throws Exception {
    for (Field field : o.getClass().getDeclaredFields()) {
      if (field.getType().isAssignableFrom(UserRepository.class)) {
        field.setAccessible(true);
        UserRepository repository;
        String repositoryKey = System.getProperty("repository");
        if ("jdbc".equals(repositoryKey)) {
          repository = new UserRepositoryJdbc();
        } else if ("sjdbc".equals(repositoryKey)) {
          repository = new UserRepositorySJdbc();
        } else {
          throw new RuntimeException("Неверно передан параметр repository");
        }
        field.set(o, repository);
      }
    }
  }
}
