package com.exemple.security.services.parametrage.organisme;

import java.util.List;

import com.exemple.security.entity.Organisme;
import com.exemple.security.playload.dto.OrganismeDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;

public interface InOrganismeService {

	List<Organisme> getAllOrganisme();

	OrganismeDTO getOrganisme(Long id);

	PageableResponseDTO getAllOrganismePagebal(int pageNo, int pageSize);

	Organisme addOrganisme(OrganismeDTO organismeDTO);

	Organisme deleteOrganismeStatut(Long id);

	Organisme updateActivites(Long id, OrganismeDTO activitesDTO);

}
