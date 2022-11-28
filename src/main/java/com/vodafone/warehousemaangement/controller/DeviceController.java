package com.vodafone.warehousemaangement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.warehousemaangement.dto.response.DeviceDtoResponse;
import com.vodafone.warehousemaangement.enumeration.DeviceStatus;
import com.vodafone.warehousemaangement.service.IDeviceService;

@RestController
@RequestMapping("/devices")
public class DeviceController {

	private final IDeviceService deviceService;
	
	public DeviceController(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}
	
	/**
	 * 
	 * @return list of all devices waiting fo activation 
	 */
	@GetMapping("/status/waiting")
	public ResponseEntity<List<DeviceDtoResponse>> listAllDevicesWaitingForActivation(){
		return ResponseEntity.ok(this.deviceService.listAllDevicesWaitingForActivation());
	}
	
	/**
	 * 
	 * @param deviceId
	 * @return a word <code>success</code> to refer to operation's status
	 */
	@DeleteMapping("{deviceId}")
	public ResponseEntity<String> removeDevice(@PathVariable Integer deviceId){
		return ResponseEntity.ok("SUCCESS");
	}
	
	/**
	 * 
	 * @param deviceId
	 * @param deviceStatus
	 * @return device entity after update
	 */
	@PatchMapping("{deviceId}")
	public ResponseEntity<DeviceDtoResponse> updateDeviceStatus(@PathVariable Integer deviceId, @RequestParam DeviceStatus deviceStatus){
		return ResponseEntity.ok(this.deviceService.updateDeviceStatus(deviceId, deviceStatus));
	}
	
	/**
	 * 
	 * @return List of Devices Available For Sale
	 */
	@GetMapping("/all-valid-sale")
	public ResponseEntity<List<DeviceDtoResponse>> listAllDevicesAvailableForSale(){
		return ResponseEntity.ok(this.deviceService.listAllDevicesAvailableForSale());
	}
	
}
