package com.exemple.security.services.parametrage.Employes;

import java.util.List;

import com.exemple.security.entity.Employes;
import com.exemple.security.playload.dto.EmployesDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;

public interface InEmployesService {

	List<EmployesDTO> getAllEmployesDTOs();

	EmployesDTO getEmployes(Long id);

	PageableResponseDTO getAllEmployesPagebal(int pageNo, int pageSize);

	Employes addEmployes(EmployesDTO employes);

	Employes deleteEmployesStatut(Long id);

	EmployesDTO updateEmployesDTO(Long idAff, EmployesDTO employesDTO);

}
