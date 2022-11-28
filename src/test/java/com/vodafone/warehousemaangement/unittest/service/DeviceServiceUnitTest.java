package com.vodafone.warehousemaangement.unittest.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.vodafone.warehousemaangement.dto.SimCardDto;
import com.vodafone.warehousemaangement.dto.response.DeviceDtoResponse;
import com.vodafone.warehousemaangement.entity.Device;
import com.vodafone.warehousemaangement.entity.SimCard;
import com.vodafone.warehousemaangement.enumeration.DeviceStatus;
import com.vodafone.warehousemaangement.repository.IDeviceRepository;
import com.vodafone.warehousemaangement.repository.ISimCardRepository;
import com.vodafone.warehousemaangement.service.impl.DeviceServiceImpl;
import com.vodafone.warehousemaangement.utils.ObjectMapperUtils;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DeviceServiceUnitTest {

	@Mock
	ObjectMapperUtils mapperUtils;

	@Mock
	IDeviceRepository deviceRepository;
	
	@Mock
	ISimCardRepository simCardRepository;
	
	@InjectMocks
	DeviceServiceImpl deviceServiceImpl;
	
	SimCard simCard_1;
	Device device_1;
	SimCardDto simCardDto;
	DeviceDtoResponse dtoResponse;
	
	@BeforeEach
	public void init() {
		simCard_1 = SimCard.builder().country("Egypt").operatorCode(011).build();
		simCardDto = SimCardDto.builder().country("Egypt").operatorCode(011).build();
		device_1 = Device.builder().deviceId(1010).deviceStatus(DeviceStatus.READY).temperature(40).simCard(simCard_1).build();
		dtoResponse = DeviceDtoResponse.builder().deviceId(1010).deviceStatus(DeviceStatus.READY).temperature(40).simCard(simCardDto).build();
	}

	@Test
    public void DeviceService_listAllDevicesWaiting_ReturnsDeviceDto_List() {
		
        when(deviceRepository.findByDeviceStatus(DeviceStatus.WAITING_FOR_ACTIVATION)).thenReturn(List.of(device_1));
        when(mapperUtils.mapAll(anyList(), eq(DeviceDtoResponse.class))).thenReturn(List.of(DeviceDtoResponse.builder().build()));
        List<DeviceDtoResponse> list = deviceServiceImpl.listAllDevicesWaitingForActivation();
        Assertions.assertThat(list).isNotNull();

    }

	@Test
    public void DeviceService_DeleteDeviceById_ReturnVoid() {
		
		device_1.setDeviceId(1001);
		when(deviceRepository.findById(anyInt())).thenReturn(Optional.of(device_1));
        doNothing().when(deviceRepository).delete(device_1);
        assertAll(() -> deviceServiceImpl.removeDevice(device_1.getDeviceId()));
    }

	@Test
    public void DeviceService_UpdateDevice_Return_ResponseDTO() {
	
		Device device_2 = Device.builder().deviceId(1010).deviceStatus(DeviceStatus.WAITING_FOR_ACTIVATION).temperature(40).simCard(simCard_1).build();
		when(deviceRepository.findById(anyInt())).thenReturn(Optional.of(device_2));
		when(mapperUtils.map(device_2, DeviceDtoResponse.class)).thenReturn(dtoResponse);
		
		DeviceDtoResponse deviceDtoResponse = deviceServiceImpl.updateDeviceStatus(device_2.getDeviceId(), DeviceStatus.READY);
        Assertions.assertThat(deviceDtoResponse).isNotNull();
        assertEquals(DeviceStatus.READY, deviceDtoResponse.getDeviceStatus());
        assertEquals(device_2.getTemperature(), deviceDtoResponse.getTemperature());
		
	}	
	
	@Test
	public void DeviceService_listAllDevicesForSale_ReturnsDeviceDto_List() {
		
        when(deviceRepository.findByDeviceStatusAndTemperatureBetween(any(),anyInt(),anyInt())).thenReturn(List.of(device_1));
        when(mapperUtils.mapAll(anyList(), eq(DeviceDtoResponse.class))).thenReturn(List.of(DeviceDtoResponse.builder().build()));
        
        List<DeviceDtoResponse> list = deviceServiceImpl.listAllDevicesAvailableForSale();
        Assertions.assertThat(list).isNotNull();
        
	}
	
}
