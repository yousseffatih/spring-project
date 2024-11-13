package com.exemple.security.playload.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RapportBody {
	private String libelleRapport;
	private String type;
	private Map<String, Object> params;
}
