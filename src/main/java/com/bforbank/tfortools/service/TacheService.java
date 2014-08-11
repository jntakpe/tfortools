package com.bforbank.tfortools.service;

import com.bforbank.tfortools.domain.Tache;
import com.bforbank.tfortools.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Services associés à l'entité {@link com.bforbank.tfortools.domain.Tache}
 *
 * @author jntakpe
 */
@Service
public class TacheService {

    @Autowired
    private TacheRepository tacheRepository;

    @Transactional(readOnly = true)
    public List<Tache> findByUtilisateurId(Long id) {
        return tacheRepository.findByUtilisateur_Id(id);
    }

    @Transactional
    public Tache save(Tache tache) {
        return tacheRepository.save(tache);
    }

    @Transactional
    public void delete(Long id) {
        tacheRepository.delete(id);
    }

}
