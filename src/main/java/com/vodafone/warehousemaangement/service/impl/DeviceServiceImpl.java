package com.vodafone.warehousemaangement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vodafone.warehousemaangement.dto.response.DeviceDtoResponse;
import com.vodafone.warehousemaangement.entity.Device;
import com.vodafone.warehousemaangement.enumeration.DeviceStatus;
import com.vodafone.warehousemaangement.exception.DeviceNoyFoundException;
import com.vodafone.warehousemaangement.repository.IDeviceRepository;
import com.vodafone.warehousemaangement.service.IDeviceService;
import com.vodafone.warehousemaangement.utils.ObjectMapperUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeviceServiceImpl implements IDeviceService {

	private final IDeviceRepository deviceRepository;
	private final ObjectMapperUtils objectMapperUtils;

	public DeviceServiceImpl(IDeviceRepository deviceRepository, ObjectMapperUtils objectMapperUtils) {
		this.deviceRepository = deviceRepository;
		this.objectMapperUtils = objectMapperUtils;
	}

	@Override
	public List<DeviceDtoResponse> listAllDevicesWaitingForActivation() {

		List<DeviceDtoResponse> response = new ArrayList<>();
		try {
			List<Device> list = this.deviceRepository.findByDeviceStatus(DeviceStatus.WAITING_FOR_ACTIVATION);
			response = this.objectMapperUtils.mapAll(list ,DeviceDtoResponse.class);
//			response = this.objectMapperUtils.mapList(list ,DeviceDtoResponse.class);

		} catch (Exception e) {
			log.error("listAllDevicesWaitingForActivation - Error: {}", e);
			throw e;
		}
		return response;
	}

	@Override
	public void removeDevice(Integer deviceId) {

		try {
			// validate if device Id exist
			Optional<Device> findByIdOptional = this.deviceRepository.findById(deviceId);
			if (findByIdOptional.isEmpty()) {
				throw new DeviceNoyFoundException(String.format("provided device Id %s not exist", deviceId));
			}
			this.deviceRepository.delete(findByIdOptional.get());
		} catch (Exception e) {
			log.error("removeDevice - Error: {}", e);
			throw e;
		}

	}

	@Override
	public DeviceDtoResponse updateDeviceStatus(Integer deviceId, DeviceStatus deviceStatus) {

		DeviceDtoResponse response = null;
		try {
			// validate if device Id exist
			Optional<Device> findByIdOptional = this.deviceRepository.findById(deviceId);
			if (findByIdOptional.isEmpty()) {
				throw new DeviceNoyFoundException(String.format("provided device Id %s not exist", deviceId));
			}
			Device device = findByIdOptional.get();
			device.setDeviceStatus(deviceStatus);
			device = this.deviceRepository.save(device);
			response = this.objectMapperUtils.map(device, DeviceDtoResponse.class);

		} catch (Exception e) {
			log.error("updateDeviceStatus - Error: {}", e);
			throw e;
		}
		return response;
	}

	@Override
	public List<DeviceDtoResponse> listAllDevicesAvailableForSale() {

		List<DeviceDtoResponse> response = new ArrayList<>();
		try {
			List<Device> list = this.deviceRepository.findByDeviceStatusAndTemperatureBetween(DeviceStatus.READY, -25, 85);
			response = this.objectMapperUtils.mapAll(list,DeviceDtoResponse.class);

		} catch (Exception e) {
			log.error("listAllDevicesWaitingForActivation - Error: {}", e);
			throw e;
		}
		return response;
	}

}
