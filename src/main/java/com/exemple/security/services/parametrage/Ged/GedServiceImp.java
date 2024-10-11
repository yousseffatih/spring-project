package com.exemple.security.services.parametrage.Ged;

import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.Ged;
import com.exemple.security.entity.User;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.File;
import com.exemple.security.playload.dto.GedDTO;
import com.exemple.security.playload.dto.SaveGedBodyDTO;
import com.exemple.security.repository.GedRepository;
import com.exemple.security.repository.UserRepository;

@Service
public class GedServiceImp implements InGedServices{
	
	
	@Autowired
	private GedRepository gedRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	@Override
	public List<GedDTO> saveImages(SaveGedBodyDTO saveGedBodyDTO)  throws IOException {
		User user= userRepository.findById(saveGedBodyDTO.getIdUser()).orElseThrow(() -> new ResourceNotFoundException("User", "id", saveGedBodyDTO.getIdUser()));
		List<Ged> list = saveGedBodyDTO.getFiles().stream().map( e -> saveImage(e, saveGedBodyDTO.getName(), saveGedBodyDTO.getId(), user)).collect(Collectors.toList());
		return list.stream().map(e -> mapToDTO(e)).collect(Collectors.toList());	
	}
	
 	private static String path = "C:/KAY/images/";
    //private static String path = "/Users/mac/uploads/images/";
	
	
	private Ged saveImage(File fileData , String name , Long id , User user)
	{
		byte[] imageBytes = Base64.getDecoder().decode(fileData.getBase64File());
		try {
			Path dirPath = Paths.get(path + name);
			
	        if (!Files.exists(dirPath)) {
	            Files.createDirectories(dirPath);
	        }
	        
	        String uniqueId = UUID.randomUUID().toString();
	        String fileName =  name + "_"+ id.toString() +"_"+ uniqueId +"."+fileData.getExtention();
	        Path filePath = dirPath.resolve(fileName);
	        Files.write(filePath, imageBytes);
	        
	        Ged ged = new Ged();
	        ged.setFileName(fileName);
	        ged.setContextFile("/KAY/images/"+ name + "/"+ fileName);
	        ged.setDateUpload(new Date());
	        ged.setDateCreation(new Date());
	        ged.setUser(user);
	        ged.setStatut(GlobalConstants.STATUT_ACTIF);
	        return gedRepository.save(ged);
	        
		} catch (IOException e) {
            throw new RuntimeException("Failed to store file " + e.toString());
		}
	}
	
//	@Override
//	public List<GedDTO> saveImages(SaveGedBodyDTO saveGedBodyDTO)  throws IOException {
//		User user= userRepository.findById(saveGedBodyDTO.getIdUser()).orElseThrow(() -> new ResourceNotFoundException("User", "id", saveGedBodyDTO.getIdUser()));
//		List<Ged> list = saveGedBodyDTO.getList().stream().map( e -> saveImage(e, saveGedBodyDTO.getName(), saveGedBodyDTO.getId(), user)).collect(Collectors.toList());
//		return list.stream().map(e -> mapToDTO(e)).collect(Collectors.toList());	
//	}
//	
// 	private static String path = "C:/KAY/images/";
//    //private static String path = "/Users/mac/uploads/images/";
//	
//	
//	private Ged saveImage(String base64 , String name , Long id , User user)
//	{
//		byte[] imageBytes = Base64.getDecoder().decode(base64);
//		try {
//			Path dirPath = Paths.get(path + name);
//			
//	        if (!Files.exists(dirPath)) {
//	            Files.createDirectories(dirPath);
//	        }
//	        
//	        String uniqueId = UUID.randomUUID().toString();
//	        String fileName =  name + "_"+ id.toString() +"_"+ uniqueId +".jpg";
//	        Path filePath = dirPath.resolve(fileName);
//	        Files.write(filePath, imageBytes);
//	        
//	        Ged ged = new Ged();
//	        ged.setFileName(fileName);
//	        ged.setContextFile("/KAY/images/"+ name + "/"+ fileName);
//	        ged.setDateUpload(new Date());
//	        ged.setDateCreation(new Date());
//	        ged.setUser(user);
//	        ged.setStatut(GlobalConstants.STATUT_ACTIF);
//	        return gedRepository.save(ged);
//	        
//		} catch (IOException e) {
//            throw new RuntimeException("Failed to store file " + e.toString());
//		}
//	}
	
	@Override
	public List<GedDTO> getAllGed(String fielName)
	{
		return gedRepository.findAllByStartFileName(fielName).stream().map(e -> mapToDTO(e)).collect(Collectors.toList());
	}
	
	@Override
	public void deleteGed(Long id) {
		Ged  ged = gedRepository.findByIdStatut(id).orElseThrow(() -> new ResourceNotFoundException("Ged", "id", id));
		ged.setStatut(GlobalConstants.STATUT_DELETE);
		ged.setDateDesactivation(new Date());
		gedRepository.save(ged);
	}
	
	private GedDTO mapToDTO(Ged x)
	{
		GedDTO dto = new GedDTO();
		dto.setId(x.getId());
		dto.setDateUpload(x.getDateUpload());
		dto.setIdUser(x.getUser().getId());
		dto.setLibelleUser(x.getUser().getLibelle());
		dto.setFileName(x.getFileName());
		dto.setFileContext("http://192.168.1.16:8080" +x.getContextFile());
		return dto;
	}
 }
