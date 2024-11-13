package com.exemple.security.services.rapport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.exemple.security.entity.Rapport;
import com.exemple.security.playload.dto.RapportBody;
import com.exemple.security.repository.CourrierRepository;
import com.exemple.security.repository.RapportRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;





@Service
public class RapportService implements InRapportService{

	@Autowired
	private RapportRepository rapportRepository;

	@Autowired
	private CourrierRepository courrierRepository;

	@Autowired
    private DataSource dataSource;




	@Override
	public List<Rapport> getAllRapports()	
	{
		List<Rapport> rapports = rapportRepository.findAllWithStatut();
		return rapports;
	}

	@Override
	public byte[] exportRaport(RapportBody rapportBody) throws FileNotFoundException , JRException {
		File file = ResourceUtils.getFile("src/main/resources/static/"+ rapportBody.getLibelleRapport() +".jrxml");
	    JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

    	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, rapportBody.getParams(), getDataSource());

	    // Use ByteArrayOutputStream to hold the PDF data in memory
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();	  
	    if ("pdf".equalsIgnoreCase(rapportBody.getType())) {
	    	JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        }
	    if ("xls".equalsIgnoreCase(rapportBody.getType())) {
        	 JRXlsxExporter xlsxExporter = new JRXlsxExporter();
             xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
             xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
             xlsxExporter.exportReport();
        }

	    return outputStream.toByteArray();
	}
	
	

	private java.sql.Connection getDataSource() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error obtaining database connection", e);
        }
    }

	


//	@Override
//	public void exportRaport() throws FileNotFoundException , JRException {
//
//		String path = "src/main/resources/static/fiels";
//
//		List<ListCourrierDTO> courriers = courrierRepository.findFilteredCourrierRa(null, null, null, null, null, null, null);
//
//		File file = ResourceUtils.getFile("src/main/resources/static/courriers_processus.jrxml");
//		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//
//		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(courriers);
//
//		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,dataSource);
//
//		JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/report.pdf" );
//
//	}


}
