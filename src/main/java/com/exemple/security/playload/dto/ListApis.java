package com.exemple.security.playload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ListApis {
	private Long id;
	private String libelle;
	private String code;
}
