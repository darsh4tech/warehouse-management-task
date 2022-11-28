package com.vodafone.warehousemaangement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vodafone.warehousemaangement.entity.Device;
import com.vodafone.warehousemaangement.enumeration.DeviceStatus;

@Repository
public interface IDeviceRepository extends JpaRepository<Device, Integer>{

	List<Device> findByDeviceStatus(DeviceStatus cardStatus);
	List<Device> findByDeviceStatusAndTemperatureBetween(DeviceStatus cardStatus, Integer tmperature1, Integer tmperature2);
	
}
