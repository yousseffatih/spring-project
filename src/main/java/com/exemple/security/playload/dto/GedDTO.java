package com.exemple.security.playload.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GedDTO {

	private Long id;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateUpload;

	private Long idUser;

	private String libelleUser;

	private String fileName;

	private String fileContext;

	private String type;
}
