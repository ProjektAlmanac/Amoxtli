import io.github.projektalmanac.amoxtli.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmailAndPasswordHash() {
        // Crear un usuario de prueba
        User user = new User();
        user.setEmail("test@example.com");
        user.setPasswordHash("passwordHash");
        entityManager.persist(user);
        entityManager.flush();

        // Realizar la búsqueda en el repositorio
        Optional<User> foundUser = userRepository.findByEmailAndPasswordHash("test@example.com", "passwordHash");

        // Verificar que el usuario se encuentra
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
        assertThat(foundUser.get().getPasswordHash()).isEqualTo("passwordHash");
    }

    @Test
    public void testFindByEmailAndPasswordHash_Invalid() {
        // Intentar encontrar un usuario con credenciales inválidas
        Optional<User> foundUser = userRepository.findByEmailAndPasswordHash("nonexistent@example.com", "invalidPasswordHash");

        // Verificar que no se encuentra ningún usuario
        assertThat(foundUser).isEmpty();
    }
}
