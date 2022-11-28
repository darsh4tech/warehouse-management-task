package com.vodafone.warehousemaangement.dto;

import java.io.Serializable;

import com.vodafone.warehousemaangement.enumeration.DeviceStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeviceDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer simId;
	private Integer operatorCode;
	private String country;
	private DeviceStatus simCardStatus;
	
}
