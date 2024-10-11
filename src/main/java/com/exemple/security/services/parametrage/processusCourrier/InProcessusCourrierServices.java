package com.exemple.security.services.parametrage.processusCourrier;

import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.ProcessusCourrierDTO;
import com.exemple.security.playload.dto.ValidePCdto;

public interface InProcessusCourrierServices {

	public ProcessusCourrierDTO getProcessusCourrier(Long id);

	PageableResponseDTO getAllProcessusCourrier(int pageNo, int pageSize, Long idCourrier);

	void validateProcessusCourrier(ValidePCdto validePCdto);
	
}
