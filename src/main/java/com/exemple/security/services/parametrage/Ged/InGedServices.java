package com.exemple.security.services.parametrage.Ged;

import java.io.IOException;
import java.util.List;

import com.exemple.security.playload.dto.GedDTO;
import com.exemple.security.playload.dto.SaveGedBodyDTO;

public interface InGedServices {

	public List<GedDTO> saveImages(SaveGedBodyDTO saveGedBodyDTO) throws IOException;

	List<GedDTO> getAllGed(String fielName);

	void deleteGed(Long id);

}
