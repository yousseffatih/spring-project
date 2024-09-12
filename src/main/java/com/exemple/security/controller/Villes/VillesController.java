package com.exemple.security.controller.Villes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.security.entity.Villes;
import com.exemple.security.playload.PageableResponseDTO;
import com.exemple.security.playload.VillesDTO;
import com.exemple.security.services.parametrage.villes.InVillesServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/villes")
@RequiredArgsConstructor
public class VillesController {
	
	@Autowired 
	private InVillesServices villesServices;
	
	
	@PostMapping
	private ResponseEntity<Villes> addVilles(@RequestBody VillesDTO villes)
	{
		Villes ville = villesServices.addVilles(villes);
		return new ResponseEntity<Villes>(ville , HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<Villes> getVille(@PathVariable Long id)
	{
		Villes villes = villesServices.getVille(id);
		return new ResponseEntity<Villes>(villes, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	private ResponseEntity<List<Villes>> getAllVillesEntity()
	{
		List<Villes> villes = villesServices.getAllVilles();
		return new ResponseEntity<>(villes , HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Villes> updateVille(@PathVariable Long id, @RequestBody VillesDTO villeDetails) {
        Villes updatedVille = villesServices.updateVille(id, villeDetails);
        return new ResponseEntity<>(updatedVille,HttpStatus.OK);
    }
	
	 @DeleteMapping("/{id}")
	 public ResponseEntity<Void> deleteVille(@PathVariable Long id)
	 {
		 villesServices.deleteVilles(id);
		 return ResponseEntity.noContent().build();
	 }
	 
	 @GetMapping("/delete/{id}")
	 public ResponseEntity<Villes> deleteVillesStatus(@PathVariable Long id)
	 {
		 Villes villes = villesServices.deleteVillesStatus(id);
		 return new ResponseEntity<Villes>(villes , HttpStatus.OK);
	 }
	 
	 @GetMapping("/allPagable")
		private ResponseEntity<PageableResponseDTO> getAllVillesPageable(
				@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo,
				@RequestParam(value = "pageSize" , defaultValue = "5" , required = false) int pageSize)
		{
			PageableResponseDTO villes = villesServices.getAllVillesPagebal(pageNo,pageSize);
			return new ResponseEntity<>(villes , HttpStatus.OK);
		}

}
