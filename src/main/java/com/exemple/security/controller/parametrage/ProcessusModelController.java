package com.exemple.security.controller.parametrage;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.security.constants.GlobalConstants;
import com.exemple.security.playload.MessageResponse;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.ProcessusModelDTO;
import com.exemple.security.repository.ProcessusModelRepository;
import com.exemple.security.services.parametrage.ProcessusModel.InProcessusModelService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/processusModel")
@RequiredArgsConstructor
public class ProcessusModelController {
	

	@Autowired
	private InProcessusModelService processusModelService;
	
	@Autowired
	private ProcessusModelRepository processusModelRepository;
	
	
	@GetMapping("/{id}")
	private ResponseEntity<ProcessusModelDTO> getVille(@PathVariable Long id)
	{
		ProcessusModelDTO processusModelDTO = processusModelService.getProcessusModel(id);
		
		return new ResponseEntity<ProcessusModelDTO>(processusModelDTO, HttpStatus.OK);
	}
	
	
	@GetMapping("/all")
	public List<ProcessusModelDTO> getAllActivites() {
		return processusModelService.getAllProcessusModel();
	}
	
	 @GetMapping("/allPagable")
	public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
			@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
	{
		PageableResponseDTO processusModel = processusModelService.getAllProcessusModelPagebal(pageNo,pageSize);
		return new ResponseEntity<>(processusModel , HttpStatus.OK);
	}
	 
	 @GetMapping("/allPagable/idTypeC={id}")
		public ResponseEntity<PageableResponseDTO> getAllActivitesPageable(
				@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
				@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize,
				@PathVariable Long id)
		{
			PageableResponseDTO processusModel = processusModelService.getAllProcessusModelByTypePagebal(pageNo,pageSize, id);
			return new ResponseEntity<>(processusModel , HttpStatus.OK);
		}
	 
	 @GetMapping("/delete/{id}")
	 public ResponseEntity<?> deleteVillesStatus(@PathVariable Long id)
	 {
		 processusModelService.deleteProcessusModelStatut(id);
		 return new ResponseEntity<>(new MessageResponse("Processus Model supprimée.","success") , HttpStatus.OK);
	 }
	 
	 @PostMapping
	 public ResponseEntity<?> addProcessusModel(@Valid @RequestBody ProcessusModelDTO processusModelDTO)
	 {
		 if(processusModelRepository.existsByCodeAdd(processusModelDTO.getCode()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Code existe déjà !" , "warning"));
		}
		 if(processusModelRepository.existsByLibelleAdd(processusModelDTO.getLibelle()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Libelle existe déjà !" , "warning"));
		}
		 if(processusModelRepository.existsByOrderProADD(processusModelDTO.getOrderPM()))
		{
			return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Order existe déjà !" , "warning"));
		}
		processusModelService.addProcessusModel(processusModelDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Processus Model ajoutée.","success"));
	 }
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateActivites(@PathVariable Long id, @Valid @RequestBody ProcessusModelDTO processusModelDTO) {
			if(processusModelRepository.existsByCodeModif(processusModelDTO.getCode(),id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Code existe déjà !" , "warning"));
			}
			if(processusModelRepository.existsByLibelleModif(processusModelDTO.getLibelle(), id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Libelle existe déjà !" , "warning"));
			}
			if(processusModelDTO.getStatut() == null || processusModelDTO.getStatut().isEmpty())
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Statut est obligatoire !" , "warning"));
			}
			if(processusModelRepository.existsByOrderProMod(processusModelDTO.getOrderPM() , id))
			{
				return ResponseEntity.status(GlobalConstants.HTTPSTATUT_RESPONSE_ERORR).body(new MessageResponse("Order existe déjà !" , "warning"));
			}
	        processusModelService.updateProcessusModel(id, processusModelDTO) ;
	        return new ResponseEntity<>(new MessageResponse("Processus modifiée.","success"),HttpStatus.OK);
	   }
	 
	 @GetMapping("/listProcessusModel")
		public List<ListApis> getAllActivitesApis(@RequestParam(value = "Id" , defaultValue = "5" , required = true) Long id) {
			List<ListApis> listApis = processusModelService.getAllByTypeIdApis(id);
			return listApis;
		}
	 
		private ListApis mapToApisList(ProcessusModelDTO activites)
		{
			ListApis listApis = new ListApis();
			listApis.setId(activites.getId());
			listApis.setCode(activites.getCode());
			listApis.setLibelle(activites.getLibelle());
			return listApis;
		}
}
