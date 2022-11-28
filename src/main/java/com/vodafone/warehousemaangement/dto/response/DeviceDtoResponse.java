package com.vodafone.warehousemaangement.dto.response;

import java.io.Serializable;

import com.vodafone.warehousemaangement.dto.SimCardDto;
import com.vodafone.warehousemaangement.enumeration.DeviceStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDtoResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer deviceId;
	private Integer temperature;
	private DeviceStatus deviceStatus;
	private SimCardDto simCard;
}
