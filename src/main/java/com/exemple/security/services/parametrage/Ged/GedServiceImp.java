package com.exemple.security.services.parametrage.Ged;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.entity.Ged;
import com.exemple.security.entity.User;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.playload.dto.Documents;
import com.exemple.security.playload.dto.GedDTO;
import com.exemple.security.playload.dto.SaveGedBodyDTO;
import com.exemple.security.repository.GedRepository;
import com.exemple.security.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class GedServiceImp implements InGedServices{


	@Autowired
	private GedRepository gedRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Value("${file.upload.dir}")
	private String path;



	@Override
	@Transactional
	public void saveImages(SaveGedBodyDTO saveGedBodyDTO) throws IOException{
		User user= userRepository.findById(saveGedBodyDTO.getIdUser()).orElseThrow(() -> new ResourceNotFoundException("User", "id", saveGedBodyDTO.getIdUser()));
		List<Ged> list = saveGedBodyDTO.getFiles().stream().map( e -> saveImage(e, saveGedBodyDTO.getName(), saveGedBodyDTO.getId(), user)).collect(Collectors.toList());

	}

 	//private static String path = "${file.upload-dir}";
    //private static String path = "/Users/mac/uploads/images/";


	private Ged saveImage(Documents fileData , String name , Long id , User user)
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
	        ged.setDateUpload(new Date());
	        ged.setDateCreation(new Date());
	        ged.setUser(user);
	        ged.setStatut(GlobalConstants.STATUT_ACTIF);
	        ged.setType(fileData.getExtention());
	        ged = gedRepository.save(ged);

	        ged.setContextFile("COURRIERS"+ "_"+ id.toString());
	        return gedRepository.save(ged);

		} catch (IOException e) {
            throw new RuntimeException("Échec de l'enregistrement du fichier " + e.toString());
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
	@Transactional
	public List<GedDTO> getAllGed(String context) {
		return gedRepository.findAllByStartFileName(context)
	            .stream()
	            .map(e -> mapToDTO(e)

	            )
	            .collect(Collectors.toList());
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
		String fullPath = path + x.getContextFile() + File.separator + x.getFileName();
	    dto.setType(x.getType());
		String encodeBase64;
		try {
			encodeBase64 = encodeFileToBase64(fullPath);
			dto.setFileContext(encodeBase64);
		} catch (IOException e) {
			throw new RuntimeException("Failed Convert Ged to GedDTO " + e.toString());
		}
		return dto;
	}


    private  String encodeFileToBase64(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Fichier non trouvé à l'emplacement fourni: " + filePath);
        }
        byte[] fileContent = FileUtils.readFileToByteArray(file);
        String encodedFile = Base64.getEncoder().encodeToString(fileContent);
        return encodedFile;
    }

 }
