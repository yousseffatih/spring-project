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
			+ "and a.id = :val ")
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

	@Query(value = """
		    SELECT
		        c.id,
		        c.libelle,
		        c.code,
		        c.statut,
		        c.date_creation,
		        c.date_modification,
		        c.date_desactivation,
		        oed.id AS idOrgExpDestCible,
		        oed.libelle AS libelleOrgExpDestCible,
		        oed1.id AS idOrgExpDestSrc,
		        oed1.libelle AS libelleOrgExpDestSrc,
		        a.id AS idAffectations,
		        a.libelle AS libelleAffectations,
		        e.id AS idEmployes,
		        e.nom AS nomEmployes,
		        e.libelle AS libelleEmployes,
		        tc.id AS idTypeCourriers,
		        tc.libelle AS libelleTypeCourriers,
		        pc.id AS idProcessusCourrier,
		        pc.libelle AS libelleProcessusCourrier,
		        c.interne,
		        c.date_courrirer,
		        c.date_echuance,
		        c.n_courrier
		    FROM
		        courrier c
		        LEFT JOIN employes e ON c.employes_id = e.id
		        JOIN processus_courrier pc ON c.processus_courrier_id = pc.id
		        JOIN processus_model pm ON pc.processus_model_id = pm.id
		        LEFT JOIN affectations a ON c.affectations_id = a.id
		        JOIN org_exp_dest oed1 ON c.org_exp_dest_src_id = oed1.id
		        JOIN org_exp_dest oed ON c.org_exp_dest_cible_id = oed.id
		        JOIN type_courriers tc ON c.type_courriers_id = tc.id
		    WHERE
		        c.statut NOT IN ('-2')
		        AND (:nCourrier IS NULL OR c.n_courrier = :nCourrier)
		        AND (:orgExpDestSrc IS NULL OR c.org_exp_dest_src_id = :orgExpDestSrc)
		        AND (:orgExpDestCible IS NULL OR c.org_exp_dest_cible_id = :orgExpDestCible)
		        AND (:typeCourriers IS NULL OR c.type_courriers_id = :typeCourriers)
		        AND (:du IS NULL OR c.date_courrirer BETWEEN to_date(:du, 'dd/mm/yyyy') AND to_date(:au, 'dd/mm/yyyy'))
		        AND (:processusModel IS NULL OR pm.id = :processusModel)
		    ORDER BY
		        c.date_creation DESC
		""", nativeQuery = true)
	Page<ListCourrierDTO> findFilteredCourrier(
	        @Param("nCourrier") String nCourrier,
	        @Param("orgExpDestSrc") Long orgExpDestSrc,
	        @Param("orgExpDestCible") Long orgExpDestCible,
	        @Param("typeCourriers") Long typeCourriers,
	        @Param("du") String du,
	        @Param("au") String au,
	        @Param("processusModel") Long processusModel,
	        Pageable pageable);

	@Query(value = """
		    SELECT
		    c.id,
		    c.libelle,
		    c.code,
		    CASE
		        WHEN c.statut = '-1' THEN 'Supprimé'
		        WHEN c.statut = '0' THEN 'Inactif'
		        WHEN c.statut = '1' THEN 'Actif'
		        WHEN c.statut = '3' THEN 'Validé'
		        WHEN c.statut = '4' THEN 'Cloturé'
		        ELSE c.statut
		    END AS statut,
		    TO_CHAR(c.date_creation, 'dd/mm/yyyy') AS date_creation,
		    TO_CHAR(c.date_modification, 'dd/mm/yyyy') AS date_modification,
		    TO_CHAR(c.date_desactivation, 'dd/mm/yyyy') AS date_desactivation,
		    oed.id AS idOrgExpDestCible,
		    oed.libelle AS libelleOrgExpDestCible,
		    oed1.id AS idOrgExpDestSrc,
		    oed1.libelle AS libelleOrgExpDestSrc,
		    a.id AS idAffectations,
		    a.libelle AS libelleAffectations,
		    e.id AS idEmployes,
		    e.nom AS nomEmployes,
		    e.libelle AS libelleEmployes,
		    tc.id AS idTypeCourriers,
		    tc.libelle AS libelleTypeCourriers,
		    pc.id AS idProcessusCourrier,
		    pc.libelle AS libelleProcessusCourrier,
		    c.interne,
		    TO_CHAR(c.date_courrirer, 'dd/mm/yyyy') AS date_courrirer,
		    TO_CHAR(c.date_echuance, 'dd/mm/yyyy') AS date_echuance,
		    c.n_courrier
		FROM
		    courrier c
		    LEFT JOIN employes e ON c.employes_id = e.id
		    JOIN processus_courrier pc ON c.processus_courrier_id = pc.id
		    JOIN processus_model pm ON pc.processus_model_id = pm.id
		    LEFT JOIN affectations a ON c.affectations_id = a.id
		    JOIN org_exp_dest oed1 ON c.org_exp_dest_src_id = oed1.id
		    JOIN org_exp_dest oed ON c.org_exp_dest_cible_id = oed.id
		    JOIN type_courriers tc ON c.type_courriers_id = tc.id
		WHERE
		    c.statut NOT IN ('-2')
		    AND c.date_courrirer BETWEEN TO_DATE('01/01/2024', 'dd/mm/yyyy') AND TO_DATE('31/12/2024', 'dd/mm/yyyy')
		ORDER BY
		    c.date_creation DESC;
		""", nativeQuery = true)
	List<ListCourrierDTO>  findFilteredCourrierRa(
	        @Param("nCourrier") String nCourrier,
	        @Param("orgExpDestSrc") Long orgExpDestSrc,
	        @Param("orgExpDestCible") Long orgExpDestCible,
	        @Param("typeCourriers") Long typeCourriers,
	        @Param("du") String du,
	        @Param("au") String au,
	        @Param("processusModel") Long processusModel
	     );


}
