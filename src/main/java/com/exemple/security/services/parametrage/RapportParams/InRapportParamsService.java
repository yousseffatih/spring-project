package com.exemple.security.services.parametrage.RapportParams;

import java.util.List;

import com.exemple.security.entity.RapportsParams;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.RapportsParamsDTO;

public interface InRapportParamsService {

	  public List<RapportsParams> getAllRapportsParams();

	  public RapportsParamsDTO getRapportsParams(Long id);


	  public RapportsParamsDTO addRapportsParams(RapportsParamsDTO rapportsParamsDTO);

	  public RapportsParamsDTO deleteRapportsParams(Long id);

	  public RapportsParamsDTO updateTypesClients(Long id, RapportsParamsDTO rapportsParamsDTO);

	  List<RapportsParamsDTO> getAllRapportsParamsPagebal(Long idRapport);
}
