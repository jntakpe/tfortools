package com.sopra.tfortools.service;

import com.sopra.tfortools.config.TforToolsConfig;
import com.sopra.tfortools.domain.NiveauTache;
import com.sopra.tfortools.domain.StatutTache;
import com.sopra.tfortools.domain.Tache;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.generator.ValueGenerators;
import com.ninja_squad.dbsetup.operation.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.TransactionSystemException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests des services de l'entité {@link com.sopra.tfortools.domain.Tache}
 *
 * @author jntakpe
 */
@SpringApplicationConfiguration(classes = TforToolsConfig.class)
public class TacheServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TacheService tacheService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private DataSourceDestination dataSourceDestination;

    public static final Operation INSERT = insertInto("tache")
            .withGeneratedValue("id", ValueGenerators.sequence())
            .withDefaultValue("version", 0)
            .withDefaultValue("creation", new Date())
            .columns("nom", "description", "statut", "niveau", "utilisateur_id")
            .values("Coder todolist", "Coder la gestion des tâches dans TforToosl", "EN_STOCK", "IMPORTANT", 1L)
            .values("Slides formation", "Préparer les slides de Modern webapp", "EN_STOCK", "NORMAL", 1L)
            .values("Slides comite IT", "Préparer les slides du comite IT", "EN_COURS", "CRITIQUE", 1L)
            .values("Env dev", "Valider les environnements de dev", "TERMINE", "FAIBLE", 2L)
            .build();

    @BeforeClass
    public void setUp() {
        Operation operation = sequenceOf(
                deleteAllFrom("tache", "utilisateur"),
                UserDetailsServiceTest.INSERT,
                INSERT);
        DbSetup dbSetup = new DbSetup(dataSourceDestination, operation);
        dbSetup.launch();
    }

    @Test
    public void findByUtilisateurIdTestShoudFind() {
        String query = "select count(*) from tache where utilisateur_id = ";
        int count = jdbcTemplate.queryForObject(query + "1", Integer.class);
        assertThat(tacheService.findByUtilisateurId(1L)).isNotEmpty().hasSize(count);
        count = jdbcTemplate.queryForObject(query + "2", Integer.class);
        assertThat(tacheService.findByUtilisateurId(2L)).isNotEmpty().hasSize(count);
    }

    @Test
    public void findByUtilisateurIdTestShouldNotFind() {
        assertThat(tacheService.findByUtilisateurId(99L)).isEmpty();
    }

    @Test
    public void createTestShouldWork() {
        Tache testTache = new Tache();
        testTache.setCreation(new Date());
        testTache.setDescription("Tache de test");
        testTache.setNom("Super tache de test");
        testTache.setStatut(StatutTache.EN_STOCK);
        testTache.setNiveau(NiveauTache.FAIBLE);
        String countQuery = "SELECT count(*) FROM tache";
        Integer initCount = jdbcTemplate.queryForObject(countQuery, Integer.class);
        String userCountQuery = "SELECT count(*) FROM tache WHERE utilisateur_id = 1";
        Integer initUserCount = jdbcTemplate.queryForObject(userCountQuery, Integer.class);
        Tache persisted = tacheService.save(testTache, 1L);
        assertThat(persisted).isNotNull();
        assertThat(jdbcTemplate.queryForObject(countQuery, Integer.class)).isEqualTo(initCount + 1);
        assertThat(jdbcTemplate.queryForObject(userCountQuery, Integer.class)).isEqualTo(initUserCount + 1);
    }

    @Test(expectedExceptions = {TransactionSystemException.class})
    public void createTestMandatoryFieldShoudFail() {
        Tache testTache = new Tache();
        testTache.setCreation(new Date());
        testTache.setDescription("Tache de test");
        testTache.setStatut(StatutTache.EN_STOCK);
        testTache.setNiveau(NiveauTache.FAIBLE);
        tacheService.save(testTache, 99L);
    }
}
