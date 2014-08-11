package com.bforbank.tfortools.service;

import com.bforbank.tfortools.config.TforToolsConfig;
import com.bforbank.tfortools.domain.NiveauTache;
import com.bforbank.tfortools.domain.StatutTache;
import com.bforbank.tfortools.domain.Tache;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.generator.ValueGenerators;
import com.ninja_squad.dbsetup.operation.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests des services de l'entité {@link com.bforbank.tfortools.domain.Tache}
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
        assertThat(tacheService.findByUtilisateurId(1L)).isNotEmpty().hasSize(3);
        assertThat(tacheService.findByUtilisateurId(2L)).isNotEmpty().hasSize(1);
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
        testTache.setUtilisateur(utilisateurService.findById(1L));
        String countQuery = "SELECT count(*) FROM tache";
        Integer initCount = jdbcTemplate.queryForObject(countQuery, Integer.class);
        String userCountQuery = "SELECT count(*) FROM tache WHERE utilisateur_id = 1";
        Integer initUserCount = jdbcTemplate.queryForObject(userCountQuery,
                Integer.class);
        Tache persisted = tacheService.save(testTache);
        assertThat(persisted).isNotNull();
        assertThat(jdbcTemplate.queryForObject(countQuery, Integer.class)).isEqualTo(initCount + 1);
        assertThat(jdbcTemplate.queryForObject(userCountQuery, Integer.class)).isEqualTo(initUserCount + 1);
    }

    @Test
    public void createTestMandatoryFieldShoudFail() {
        Tache testTache = new Tache();
        testTache.setCreation(new Date());
        testTache.setDescription("Tache de test");
        testTache.setNom("Super tache de test");
        testTache.setStatut(StatutTache.EN_STOCK);
        testTache.setNiveau(NiveauTache.FAIBLE);
    }
}
