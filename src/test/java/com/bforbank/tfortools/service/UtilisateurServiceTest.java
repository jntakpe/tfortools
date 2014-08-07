package com.bforbank.tfortools.service;

import com.bforbank.tfortools.config.TforToolsConfig;
import com.bforbank.tfortools.domain.Role;
import com.bforbank.tfortools.domain.Utilisateur;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de la classe {@link com.bforbank.tfortools.service.UtilisateurService}
 *
 * @author jntakpe
 */
@SpringApplicationConfiguration(classes = TforToolsConfig.class)
public class UtilisateurServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private DataSourceDestination dataSourceDestination;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeClass
    public void setUp() {
        Operation operation = sequenceOf(
                deleteAllFrom("tache", "utilisateur"),
                UserDetailsServiceTest.INSERT
        );
        DbSetup dbSetup = new DbSetup(dataSourceDestination, operation);
        dbSetup.launch();
    }

    @Test
    public void createTestShouldCreateUser() {
        String countQuery = "SELECT count(*) FROM utilisateur";
        Integer initialCount = jdbcTemplate.queryForObject(countQuery, Integer.class);
        Utilisateur newUser = new Utilisateur();
        newUser.setLogin("newUser");
        newUser.setPassword("test");
        newUser.setEmail("newuser@gmail.com");
        Utilisateur persistedUser = utilisateurService.create(newUser);
        assertThat(jdbcTemplate.queryForObject(countQuery, Integer.class)).isEqualTo(initialCount + 1);
        assertThat(persistedUser).isNotNull().isEqualTo(newUser);
        assertThat(persistedUser.getRole()).isEqualTo(Role.USER);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createTestShouldNotCreateCuzNameExist() {
        assertThat(jdbcTemplate.queryForObject("SELECT count(*) FROM utilisateur WHERE login = 'toto'", Integer.class))
                .isEqualTo(1);
        Utilisateur toto = new Utilisateur();
        toto.setLogin("toto");
        toto.setPassword("test");
        toto.setEmail("newuser@gmail.com");
        utilisateurService.create(toto);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createTestShouldNotCreateCuzEmailExist() {
        assertThat(jdbcTemplate.queryForObject("SELECT count(*) FROM utilisateur WHERE email = 'toto@gmail.com'",
                Integer.class)).isEqualTo(1);
        Utilisateur toto = new Utilisateur();
        toto.setLogin("newToto");
        toto.setPassword("test");
        toto.setEmail("toto@gmail.com");
        utilisateurService.create(toto);
    }

    @Test
    public void loginExistShouldBeTrue() {
        assertThat(utilisateurService.loginExist("TOTO")).isTrue();
        assertThat(utilisateurService.loginExist("Toto")).isTrue();
        assertThat(utilisateurService.loginExist("toto")).isTrue();
        assertThat(utilisateurService.loginExist("titi")).isTrue();
    }

    @Test
    public void loginExistShoudBeFalse() {
        assertThat(utilisateurService.loginExist("unknowuser")).isFalse();
        assertThat(utilisateurService.loginExist("toto ")).isFalse();
    }

    @Test
    public void emailExistShoudBeTrue() {
        assertThat(utilisateurService.emailExist("toto@gmail.com")).isTrue();
        assertThat(utilisateurService.emailExist("TOTO@gmail.com")).isTrue();
        assertThat(utilisateurService.emailExist("Titi@GMAIL.com")).isTrue();
    }

    @Test
    public void emailExistShouldBeFalse() {
        assertThat(utilisateurService.emailExist("haha@gmail.com")).isFalse();
        assertThat(utilisateurService.emailExist("haha @gmail.com")).isFalse();
        assertThat(utilisateurService.emailExist("haha@gmail.com ")).isFalse();
    }

}
