package com.exemple.security.services.parametrage.fonctions;

import java.util.List;

import com.exemple.security.entity.Fonctions;
import com.exemple.security.playload.dto.FonctionsDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;

public interface InFonctionsServices {

	Fonctions addFonctions(FonctionsDTO fonctionsDTO);

	FonctionsDTO getFonctions(Long id);

	List<Fonctions> getAllFonctions();

	Fonctions updateFonctions(Long idFonctions, FonctionsDTO fonctionsDTO);

	void deleteFonctions(Long id);

	Fonctions deleteFonctionsStatus(Long id);

	PageableResponseDTO getAllFonctionsPagebal(int pageNo, int pageSize);


}
