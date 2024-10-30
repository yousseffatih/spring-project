package com.exemple.security.services.parametrage.Ged;

import java.io.IOException;
import java.util.List;

import com.exemple.security.playload.dto.GedDTO;
import com.exemple.security.playload.dto.SaveGedBodyDTO;

public interface InGedServices {

	void deleteGed(Long id);


	List<GedDTO> getAllGed(String context);

	void saveImages(SaveGedBodyDTO saveGedBodyDTO) throws IOException;



}
