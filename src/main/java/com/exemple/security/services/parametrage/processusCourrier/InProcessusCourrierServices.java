package com.exemple.security.services.parametrage.processusCourrier;

import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.ProcessusCourrierDTO;
import com.exemple.security.playload.dto.ValidePCdto;

public interface InProcessusCourrierServices {

	public ProcessusCourrierDTO getProcessusCourrier(Long id);

	public PageableResponseDTO getAllProcessusCourrier(int pageNo, int pageSize, Long idCourrier);

	public void validateProcessusCourrier(ValidePCdto validePCdto);

	public void annulerValidation(Long id);

}
