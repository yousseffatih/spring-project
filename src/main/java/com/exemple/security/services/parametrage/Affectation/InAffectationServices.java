package com.exemple.security.services.parametrage.Affectation;

import java.util.List;

import com.exemple.security.entity.Affectations;
import com.exemple.security.playload.dto.AffectationsDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;

public interface InAffectationServices {

	public List<AffectationsDTO> getAllAffectation();

	public AffectationsDTO getAffectation(Long id);

	public PageableResponseDTO getAllAffectationPagebal(int pageNo, int pageSize);

	public Affectations addAffectation(AffectationsDTO affectations);

	public Affectations deleteAffectationStatut(Long id);

	public AffectationsDTO updateAffectation(Long id, AffectationsDTO activitesDTO);

}
