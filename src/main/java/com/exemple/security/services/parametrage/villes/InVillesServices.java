package com.exemple.security.services.parametrage.villes;

import java.util.List;


import com.exemple.security.entity.Villes;
import com.exemple.security.playload.PageableResponseDTO;
import com.exemple.security.playload.VillesDTO;


public interface InVillesServices {
	
	 public Villes addVilles(VillesDTO villes);
	 
	 public Villes getVille(Long Id);
	 
	 
	 public Villes updateVille(Long idVilles ,VillesDTO villes);
	 
	 public void deleteVilles(Long id);

	 public List<Villes> getAllVilles();

	 public Villes deleteVillesStatus(Long id);

	 public PageableResponseDTO getAllVillesPagebal(int pageNo , int pageSize);
}