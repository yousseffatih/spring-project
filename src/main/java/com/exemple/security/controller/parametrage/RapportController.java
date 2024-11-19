package com.exemple.security.controller.parametrage;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.security.entity.Rapport;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.RapportBody;
import com.exemple.security.services.rapport.InRapportService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/rapport")
@RequiredArgsConstructor
public class RapportController {

	@Autowired
	private InRapportService rapportService;
	
	
	@GetMapping
	public List<Rapport> getAllRapports()
	{
		List<Rapport> rapports = rapportService.getAllRapports();
		return rapports;
	}


	@GetMapping("/listRapport")
	public List<ListApis> getAllActivitesApis() {
		List<Rapport> rapports  = rapportService.getAllRapports();
		List<ListApis> listApis = rapports.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
		return listApis;
	}

	@PostMapping("/generateReport")
	public void generatePdf(HttpServletResponse response , @RequestBody RapportBody rapportBody ) throws Exception {
	    try {
	        byte[] pdfData = rapportService.exportRaport(rapportBody);

	        // Set response content type and headers for inline PDF display
	        if ("pdf".equalsIgnoreCase(rapportBody.getType())) {
	            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
	            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=report.pdf");
	            response.getOutputStream().write(pdfData);
	        } else if ("xls".equalsIgnoreCase(rapportBody.getType())) {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=report.xls");
	            response.getOutputStream().write(pdfData);
	        }
	        response.getOutputStream().flush();
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception(e.getMessage());
	    }
	}

	private ListApis mapToApisList(Rapport rapport)
	{
		ListApis listApis = new ListApis();
		listApis.setId(rapport.getId());
		listApis.setCode(rapport.getCode());
		listApis.setLibelle(rapport.getLibelle());
		listApis.setEtat(rapport.getEtat());
		return listApis;
	}
}
