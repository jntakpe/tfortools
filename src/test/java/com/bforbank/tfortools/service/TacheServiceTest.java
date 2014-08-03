package com.bforbank.tfortools.service;

import com.bforbank.tfortools.config.TforToolsConfig;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.generator.ValueGenerators;
import com.ninja_squad.dbsetup.operation.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

import static com.ninja_squad.dbsetup.Operations.*;

/**
 * Tests des services de l'entité {@link com.bforbank.tfortools.domain.Tache}
 *
 * @author jntakpe
 */
@SpringApplicationConfiguration(classes = TforToolsConfig.class)
public class TacheServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TacheService tacheService;

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
            .values("Env dev", "Valider les environnements de dev", "TERMINE", "FAIBLE", 1L)
            .build();

    @BeforeClass
    public void setup() {
        Operation operation = sequenceOf(
                deleteAllFrom("tache", "utilisateur"),
                UserDetailsServiceTest.INSERT,
                INSERT);
        DbSetup dbSetup = new DbSetup(dataSourceDestination, operation);
        dbSetup.launch();
    }

    @Test
    public void findByUtilisateurIdTest() {
        System.out.println();
    }
}
