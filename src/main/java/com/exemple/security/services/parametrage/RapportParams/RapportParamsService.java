package com.exemple.security.services.parametrage.RapportParams;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.batik.bridge.AbstractSVGGradientElementBridge.Stop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.Numero;
import com.exemple.security.entity.Rapport;
import com.exemple.security.entity.RapportsParams;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.RapportsParamsDTO;
import com.exemple.security.repository.NumeroRepository;
import com.exemple.security.repository.RapportParamsRepository;
import com.exemple.security.repository.RapportRepository;
import com.exemple.security.services.parametrage.Numero.InNumeroService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class RapportParamsService implements InRapportParamsService{

	@Autowired
	private RapportParamsRepository rapportParamsRepository;

	@Autowired
	private RapportRepository rapportRepository;

	@Autowired
	private InNumeroService numeroService;

	@Autowired
	private NumeroRepository numeroRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public List<RapportsParams> getAllRapportsParams() {
		List<RapportsParams> rapportsParams = rapportParamsRepository.findAllWithStatus();
		return rapportsParams;
	}

	@Override
	public RapportsParamsDTO getRapportsParams(Long id) {
		RapportsParams rapportsParams = rapportParamsRepository.findByIdStatut(id).orElseThrow(()-> new ResourceNotFoundException("RapportsParams", "id", id));
		return mapToDTO(rapportsParams);
	}

	@Override
	public List<RapportsParamsDTO> getAllRapportsParamsPagebal(Long idRapport) {
		List<RapportsParams> v = rapportParamsRepository.findallStatutsPa(idRapport);
		List<RapportsParamsDTO> list = v.stream().map(p -> mapToDTO(p)).collect(Collectors.toList());
		return list;
	}

	@Override
	public RapportsParamsDTO addRapportsParams(RapportsParamsDTO rapportsParamsDTO) {
		RapportsParams addRapportsParams=new RapportsParams();

		Rapport rapport = rapportRepository.findByIdStatut(rapportsParamsDTO.getIdRapport()).orElse(null);

		List<Numero> numeros = numeroRepository.findByCode("RapportsParams");
		if(numeros != null && !numeros.isEmpty())
		{
			Numero numero = numeros.get(0);
			numero.setVeleur((Integer.parseInt(numero.getVeleur())  + 1)+ "");

			numeroRepository.save(numero);
			addRapportsParams.setCode(numeroService.genrateNumero(numero));
		}

		addRapportsParams.setLibelle(rapportsParamsDTO.getLibelle());
		addRapportsParams.setStatut(GlobalConstants.STATUT_ACTIF);
		addRapportsParams.setDateCreation(new Date());

		addRapportsParams.setRapport(rapport);

		addRapportsParams.setTypeParam(rapportsParamsDTO.getTypeParam());
		addRapportsParams.setApiParam(rapportsParamsDTO.getApiParam());
		addRapportsParams.setNameParam(rapportsParamsDTO.getNameParam());
		addRapportsParams.setOrderParam(rapportsParamsDTO.getOrderParam());
		addRapportsParams.setObligatoire(rapportsParamsDTO.getObligatoire());
		addRapportsParams.setDefaultValue(rapportsParamsDTO.getDefaultValue());

		return mapToDTO(rapportParamsRepository.save(addRapportsParams));
	}

	@Override
	public RapportsParamsDTO deleteRapportsParams(Long id) {
		RapportsParams rapportsParams = rapportParamsRepository.findByIdStatut(id)
                .orElseThrow(() -> new ResourceNotFoundException("RapportsParams", "id", id));
		rapportsParams.setStatut(GlobalConstants.STATUT_DELETE);
		rapportsParams.setDateDesactivation(new Date());
		return   mapToDTO(rapportParamsRepository.save(rapportsParams));
	}

	@Override
	public RapportsParamsDTO updateTypesClients(Long id, RapportsParamsDTO rapportsParamsDTO) {
		RapportsParams rapportsParams = rapportParamsRepository.findByIdStatut(id).orElseThrow(() ->  new ResourceNotFoundException("RapportsParams", "id", id));

		Rapport rapport = rapportRepository.findByIdStatut(rapportsParamsDTO.getIdRapport()).orElse(null);

		rapportsParams.setLibelle(rapportsParamsDTO.getLibelle());
		rapportsParams.setStatut(GlobalConstants.getStatusFromDescription(rapportsParamsDTO.getStatut()));
		rapportsParams.setDateModification(new Date());

		rapportsParams.setRapport(rapport);

		rapportsParams.setTypeParam(rapportsParamsDTO.getTypeParam());
		rapportsParams.setApiParam(rapportsParamsDTO.getApiParam());
		rapportsParams.setOrderParam(rapportsParamsDTO.getOrderParam());
		rapportsParams.setObligatoire(rapportsParamsDTO.getObligatoire());
		rapportsParams.setDefaultValue(rapportsParamsDTO.getDefaultValue());
		rapportsParamsDTO.setNameParam(rapportsParamsDTO.getNameParam());
		return mapToDTO(rapportParamsRepository.save(rapportsParams));
	}

	private RapportsParamsDTO mapToDTO(RapportsParams x)
	{
		RapportsParamsDTO dto = new RapportsParamsDTO();
		dto.setId(x.getId());
		dto.setCode(x.getCode());
		dto.setLibelle(x.getLibelle());
		dto.setDateCreation(x.getDateCreation());
		dto.setDateModification(x.getDateModification());
		dto.setDateDesactivation(x.getDateDesactivation());
		dto.setStatut(GlobalConstants.getStatusDescription(x.getStatut()));

		dto.setIdRapport(x.getRapport().getId());
		dto.setLibelleRapport(x.getRapport().getLibelle());
		dto.setLibelleRapport(x.getRapport().getEtat());

		dto.setTypeParam(x.getTypeParam());
		dto.setApiParam(x.getApiParam());
		dto.setNameParam(x.getNameParam());
		dto.setOrderParam(x.getOrderParam());
		dto.setObligatoire(x.getObligatoire());
		dto.setDefaultValue(x.getDefaultValue());
		
		if(x.getApiParam() != null && !x.getApiParam().isEmpty())
		{
			dto.setListDefault(getInternalApiData(x.getApiParam()));
		}

		return dto;
	}
	
	private  List<Object> getInternalApiData(String url) {
		String localUrlString = "http://localhost:8080";
	    String token = extractTokenFromRequest();
	    if (token == null) {
	        throw new IllegalStateException("JWT token not found in security context");
	    }

	    HttpHeaders headers = new HttpHeaders();
	    headers.setBearerAuth(token);
	    HttpEntity<String> entity = new HttpEntity<>(headers);

	    ResponseEntity<List<Object>> response = restTemplate.exchange(
	            (localUrlString + url),
	            HttpMethod.GET,
	            entity,
	            new ParameterizedTypeReference<List<Object>>() {}
	    );

	    return response.getBody();
	}

	private String extractTokenFromRequest() {
	    String bearerToken = request.getHeader("Authorization");
	    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
	        return bearerToken.substring(7);
	    }
	    return null;
	}

	
}
