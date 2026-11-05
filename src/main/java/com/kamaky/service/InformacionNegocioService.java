package com.kamaky.service;

import com.kamaky.domain.InformacionNegocio;
import com.kamaky.repository.InformacionNegocioRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author karias
 */

@Service
@RequiredArgsConstructor
public class InformacionNegocioService {
    private final InformacionNegocioRepository informacionNegocioRepository;

    @Transactional(readOnly = true)
    public List<InformacionNegocio> getInformacionNegocio() {
        return informacionNegocioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<InformacionNegocio> getInformacionNegocio(Integer idInformacion) {
        return informacionNegocioRepository.findById(idInformacion);
    }

    @Transactional
    public void save(InformacionNegocio informacionNegocio) {
        informacionNegocioRepository.save(informacionNegocio);
    }

    @Transactional
    public void delete(Integer idInformacion) {
        informacionNegocioRepository.deleteById(idInformacion);
    }
}
