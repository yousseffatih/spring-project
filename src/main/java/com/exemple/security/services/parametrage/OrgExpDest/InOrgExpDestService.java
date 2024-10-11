package com.exemple.security.services.parametrage.OrgExpDest;

import java.util.List;

import com.exemple.security.entity.OrgExpDest;
import com.exemple.security.playload.dto.OrgExpDestDTO;
import com.exemple.security.playload.dto.PageableResponseDTO;

public interface InOrgExpDestService {

	List<OrgExpDest> getAllOrgExpDest();

	OrgExpDest getOrgExpDest(Long id);

	PageableResponseDTO getAllOrgExpDestPagebal(int pageNo, int pageSize);

	OrgExpDest addOrgExpDest(OrgExpDestDTO orgExpDestDTO);

	OrgExpDest deleteOrgExpDestStatut(Long id);

	OrgExpDest updateorgExpDest(Long id, OrgExpDestDTO orgExpDestDTO);

}
