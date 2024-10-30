package com.exemple.security.services.parametrage.courrier;

import java.util.List;

import com.exemple.security.entity.Courrier;
import com.exemple.security.playload.dto.CourrierDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;

public interface InCourrierServcies {

	public CourrierDTO addCourrier(CourrierDTO courrierDTO);

	public List<CourrierDTO> getAllCourrier();

	public PageableResponseDTO getAllCourriersPagebal(int pageNo, int pageSize);

	public PageableResponseDTO filterCourriers(String nCourrier,Long idOrgExpDestSrc,Long idOrgExpDestCible,String dateCourrirer,String dateEchuance, Long idTypeCourriers,Long idProcessusModel,int pageNo,int pageSize);

	public Courrier deleteCourirrerStatut(Long id);

	public CourrierDTO updateCourrir(CourrierDTO courrierDTO, Long id);

	CourrierDTO getCourrierId(Long id);

}
