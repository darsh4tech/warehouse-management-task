package com.vodafone.warehousemaangement.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimCardDto implements Serializable { 
	
	private static final long serialVersionUID = 1L;

	private Integer simId;
	private Integer operatorCode;
	private String country;
	
}
