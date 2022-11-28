package com.vodafone.warehousemaangement.service;

import java.util.List;

import com.vodafone.warehousemaangement.dto.response.DeviceDtoResponse;
import com.vodafone.warehousemaangement.enumeration.DeviceStatus;

public interface IDeviceService {

	List<DeviceDtoResponse> listAllDevicesWaitingForActivation();
	void removeDevice(Integer deviceId);
	DeviceDtoResponse updateDeviceStatus(Integer deviceId, DeviceStatus deviceStatus);
	List<DeviceDtoResponse> listAllDevicesAvailableForSale();
}
