package com.exemple.security.services.rapport;

import java.io.FileNotFoundException;
import java.util.List;

import com.exemple.security.entity.Rapport;
import com.exemple.security.playload.dto.RapportBody;

import net.sf.jasperreports.engine.JRException;

public interface InRapportService {

	public List<Rapport> getAllRapports();

	byte[] exportRaport(RapportBody rapportBody) throws FileNotFoundException, JRException;



}
