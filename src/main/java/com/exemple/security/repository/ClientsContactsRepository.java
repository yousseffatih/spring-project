package com.exemple.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemple.security.entity.ClientsContacts;

@Repository
public interface ClientsContactsRepository extends JpaRepository<ClientsContacts, Long> {

	@Override
	Optional<ClientsContacts> findById(Long id);

	@Query("select a"
			+ " from ClientsContacts a "
			+ " where a.statut in ('0','1')")
	List<ClientsContacts> findAllWithStatus();


	@Query("select a"
			+ " from ClientsContacts a "
			+ " where a.statut in ('0','1')"
			+ " and a.id = :val "
			+ " order by a.libelle ")
	Optional<ClientsContacts> findByIdStatut(@Param("val") Long val);

//	@Query("select a"
//			+ " from ClientsContacts a "
//			+ " where a.statut in ('0','1') "
//			+ " order by a.libelle ")
//    Page<ClientsContacts> findallStatutsPa(Pageable pageable);


	@Query(value = """
				SELECT *
				FROM clients_contacts c
				where c.clients_id = :val
				ORDER BY date_creation DESC
			""", nativeQuery = true)
    Page<ClientsContacts> findallStatutsPa(Pageable pageable, @Param("val") Long val);


	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from ClientsContacts a "
			+ " where lower(a.libelle) = lower(:val)  and a.statut in('0','1') ")
	boolean existsByLibelleAdd(@Param("val") String val);


	@Query("select "
			+ " case when count(a)> 0 then true "
			+ " else false end "
			+ " from ClientsContacts a "
			+ " where lower(libelle) like lower(:val) "
			+ " and a.statut not in('-1','-2') "
			+ " and a.id <> :id ")
	boolean existsByLibelleModif(@Param("val") String val, @Param("id") Long id);
}
