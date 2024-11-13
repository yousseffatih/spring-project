package com.exemple.security.services.parametrage.Reseaux;

import java.util.List;

import com.exemple.security.entity.Reseaux;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.ReseauxDTO;

public interface InReseauxService {

	public Reseaux addReseaux(ReseauxDTO reseauxDTO);

	public ReseauxDTO getReseaux(Long id);

	public List<Reseaux> getAllReseaux();

	public PageableResponseDTO getAllReseauxPagebal(int pageNo, int pageSize);

	public Reseaux deleteReseauxStatut(Long id);

	public void deleteReseaux(Long id);

	public Reseaux updateReseaux(Long idReseaux, ReseauxDTO reseauxDTO);



}
