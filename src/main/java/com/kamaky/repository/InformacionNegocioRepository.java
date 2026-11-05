package com.kamaky.repository;

import com.kamaky.domain.InformacionNegocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author karias
 */

@Repository
public interface InformacionNegocioRepository extends JpaRepository<InformacionNegocio, Integer>{
    
}
