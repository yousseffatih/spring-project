package com.exemple.security.services.parametrage.Activities;

import java.util.List;

import com.exemple.security.entity.Activites;
import com.exemple.security.playload.dto.ActivitesDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;

public interface InActivitiesServices {

	public List<Activites> getAllActivities();

	public Activites getActivities(Long id);

	public PageableResponseDTO getAllActivitesPagebal(int pageNo, int pageSize);

	Activites addActivites(ActivitesDTO activites);

	Activites deleteActivitesStatut(Long id);

	Activites updateActivites(Long idActivite, ActivitesDTO activitesDTO);

}
