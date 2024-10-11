package com.exemple.security.services.parametrage.TypeCourriers;

import java.util.List;

import com.exemple.security.entity.TypeCourriers;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.TypeCourriersDTO;

public interface InTypeCourriersServices {

	List<TypeCourriers> getAllTypeCourriers();

	TypeCourriers getTypeCourriers(Long id);

	PageableResponseDTO getAllTypeCourriersPagebal(int pageNo, int pageSize);

	TypeCourriers addTypeCourriers(TypeCourriersDTO typeCourriersDTO);

	TypeCourriers deleteTypeCourriersStatut(Long id);

	TypeCourriers updateTypeCourriers(Long id, TypeCourriersDTO typeCourriersDTO);

}
