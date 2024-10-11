package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exemple.security.entity.Courrier;

import com.exemple.security.playload.dto.ListCourrierDTO;

public interface CourrierRepository extends JpaRepository<Courrier, Long>{
	
	@Query("select a"
			+ " from Courrier a "
			+ " where a.statut in ('0','1')")
	List<Courrier> findAllWithStatus();
	
	
	@Query("select a"
			+ " from Courrier a "
			+ " where (a.statut = '1' OR a.statut = '0')"
			+ "and a.id = :val")
	Optional<Courrier> findByIdStatut(@Param("val") Long val);
	
	@Query("select a"
			+ " from Courrier a "
			+ " where a.statut in ('0','1')")
    Page<Courrier> findallStatutsPa(Pageable pageable);
	
	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Courrier a "
			+ " where lower(a.code) = lower(:val)  and a.statut in('0','1')")
	boolean existsByCodeAdd(@Param("val") String val);

	
	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Courrier a "
			+ " where lower(a.libelle) = lower(:val)  and a.statut in('0','1')")
	boolean existsByLibelleAdd(@Param("val") String val);
	
	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Courrier a "
			+ " where lower(code) like lower(:val) and a.statut not in('-1','-2')"
			+ " and a.id <> :id ")
	boolean existsByCodeModif(@Param("val") String val, @Param("id") Long id);
	
	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from Courrier a "
			+ " where lower(libelle) like lower(:val) and a.statut not in('-1','-2')"
			+ " and a.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);
	
	@Query(value = "SELECT c.id, " +
            "c.libelle, " +
            "c.code, " + 
            "c.statut, " +
            "c.date_creation, " +
            "c.date_modification, " +
            "c.date_desactivation, "  + 
            "oed.id AS idOrgExpDestCible, " +
            "oed.libelle AS libelleOrgExpDestCible, " +
            "oed1.id AS idOrgExpDestSrc, " + 
            "oed1.libelle AS libelleOrgExpDestSrc, " +
            "a.id AS idAffectations, " + 
            "a.libelle AS libelleAffectations, " +
            "e.id AS idEmployes, " +
            "e.nom AS nomEmployes, " +
            "e.libelle AS libelleEmployes, " +
            "tc.id AS idTypeCourriers, " +
            "tc.libelle AS libelleTypeCourriers, " +
            "pc.id AS idProcessusCourrier, " +
            "pc.libelle AS libelleProcessusCourrier, " +
            "c.interne, " +
            "c.date_courrirer, " +
            "c.date_echuance, " +
            "c.n_courrier "
			+ "FROM courrier c ,processus_courrier pc , processus_model pm , employes e , affectations a , org_exp_dest oed1 , org_exp_dest oed , type_courriers tc "
			+ "WHERE c.employes_id = e.id "
			+ "AND c.affectations_id = a.id "
			+ "AND c.org_exp_dest_src_id = oed1.id "
			+ "AND c.org_exp_dest_cible_id = oed.id "
			+ "AND c.type_courriers_id = tc.id "
			+ "AND c.processus_courrier_id = pc.id "
			+ "AND pc.processus_model_id = pm.id "
			+ "AND c.statut IN ('0','1') "
			+ "AND (:nCourrier IS NULL OR c.n_courrier = :nCourrier) "
			+ "AND (:orgExpDestSrc IS NULL OR c.org_exp_dest_src_id = :orgExpDestSrc) "
			+ "AND (:orgExpDestCible IS NULL OR c.org_exp_dest_cible_id = :orgExpDestCible) "
			+ "AND (:typeCourriers IS NULL OR c.type_courriers_id = :typeCourriers) "
			+ "AND (:du IS NULL OR c.date_courrirer BETWEEN to_date(:du, 'dd/mm/yyyy') AND to_date(:au, 'dd/mm/yyyy')) " 
			+ "AND (:processusModel IS NULL OR pm.id = :processusModel)", nativeQuery = true)
	Page<ListCourrierDTO> findFilteredCourrier(
	        @Param("nCourrier") String nCourrier,
	        @Param("orgExpDestSrc") Long orgExpDestSrc,
	        @Param("orgExpDestCible") Long orgExpDestCible,
	        @Param("typeCourriers") Long typeCourriers,
	        @Param("du") String du,
	        @Param("au") String au, 
	        @Param("processusModel") Long processusModel,
	        Pageable pageable);

	
}