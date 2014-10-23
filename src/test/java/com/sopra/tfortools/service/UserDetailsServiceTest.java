package com.sopra.tfortools.service;

import com.sopra.tfortools.config.TforToolsConfig;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.generator.ValueGenerators;
import com.ninja_squad.dbsetup.operation.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de la classe {@link org.springframework.security.core.userdetails.UserDetailsService}
 *
 * @author jntakpe
 */
@SpringApplicationConfiguration(classes = TforToolsConfig.class)
public class UserDetailsServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Destination dataSourceDestination;

    public static final Operation INSERT = insertInto("utilisateur")
            .withGeneratedValue("id", ValueGenerators.sequence())
            .withDefaultValue("version", 0)
            .withDefaultValue("derniere_connexion", null)
            .columns("email", "login", "password", "role")
            .values("toto@gmail.com", "toto", "test", "ADMIN")
            .values("titi@gmail.com", "titi", "test", "USER")
            .values("tata@gmail.com", "tata", "test", "USER")
            .values("tutu@gmail.com", "tutu", "test", "USER")
            .build();


    @BeforeClass
    public void setUp() {
        Operation operation = sequenceOf(
                deleteAllFrom("tache", "utilisateur"),
                INSERT
        );
        DbSetup dbSetup = new DbSetup(dataSourceDestination, operation);
        dbSetup.launch();
    }

    @Test
    public void loadByUsernameTestUserSuccess() {
        String username = "toto";
        UserDetails user = userDetailsService.loadUserByUsername(username);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.isAccountNonLocked()).isTrue();
        assertThat(user.isAccountNonExpired()).isTrue();
        assertThat(user.isAccountNonLocked()).isTrue();
        assertThat(user.isCredentialsNonExpired()).isTrue();
        assertThat(user.getAuthorities()).hasSize(1);
        assertThat(new ArrayList<>(user.getAuthorities()).get(0).getAuthority()).isEqualTo("ADMIN");
    }

    @Test
    public void loadByUsernameTestAdminSuccess() {
        String username = "titi";
        UserDetails user = userDetailsService.loadUserByUsername(username);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.isAccountNonLocked()).isTrue();
        assertThat(user.isAccountNonExpired()).isTrue();
        assertThat(user.isAccountNonLocked()).isTrue();
        assertThat(user.isCredentialsNonExpired()).isTrue();
        assertThat(user.getAuthorities()).hasSize(1);
        assertThat(new ArrayList<>(user.getAuthorities()).get(0).getAuthority()).isEqualTo("USER");
    }

    @Test(expectedExceptions = UsernameNotFoundException.class)
    public void loadByUsernameTestFail() {
        String username = "unknownuser";
        userDetailsService.loadUserByUsername(username);
    }
}
