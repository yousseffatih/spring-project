package com.exemple.security.services.parametrage.ProcessusModel;

import java.util.List;

import com.exemple.security.entity.ProcessusModel;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.ProcessusModelDTO;

public interface InProcessusModelService {

	List<ProcessusModelDTO> getAllProcessusModel();

	ProcessusModelDTO getProcessusModel(Long id);

	PageableResponseDTO getAllProcessusModelPagebal(int pageNo, int pageSize);

	ProcessusModel addProcessusModel(ProcessusModelDTO processusModelDTO);

	ProcessusModel deleteProcessusModelStatut(Long id);

	ProcessusModelDTO updateProcessusModel(Long idAff, ProcessusModelDTO processusModelDTO);

	List<ListApis> getAllByTypeIdApis(Long id);

	PageableResponseDTO getAllProcessusModelByTypePagebal(int pageNo, int pageSize, Long id);

}
