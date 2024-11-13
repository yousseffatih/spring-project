package com.exemple.security.services.parametrage.TypesRapports;

import java.util.List;

import com.exemple.security.entity.TypesRapport;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.TypesRapportDTO;

public interface InTypesRapportsService {

	public List<TypesRapport> getAllTypesRapports();

	public TypesRapportDTO getTypesRapports(Long id);

	public PageableResponseDTO getAllTypesRapportsPagebal(int pageNo, int pageSize);

	TypesRapportDTO addTypesRapports(TypesRapportDTO activites);

	TypesRapportDTO deleteTypesRapportsStatut(Long id);

	TypesRapportDTO updateTypesRapports(Long idActivite, TypesRapportDTO activitesDTO);
}
