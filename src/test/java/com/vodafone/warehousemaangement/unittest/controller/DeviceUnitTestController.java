package com.vodafone.warehousemaangement.unittest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.vodafone.warehousemaangement.controller.DeviceController;
import com.vodafone.warehousemaangement.dto.SimCardDto;
import com.vodafone.warehousemaangement.dto.response.DeviceDtoResponse;
import com.vodafone.warehousemaangement.entity.Device;
import com.vodafone.warehousemaangement.entity.SimCard;
import com.vodafone.warehousemaangement.enumeration.DeviceStatus;
import com.vodafone.warehousemaangement.service.impl.DeviceServiceImpl;

@WebMvcTest(controllers = DeviceController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DeviceUnitTestController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DeviceServiceImpl deviceServiceImpl;
	
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
    public void DeviceService_listAllDevicesWaiting_ReturnsDeviceDto_List() throws Exception {

		List<DeviceDtoResponse> list = List.of(dtoResponse);
		when(deviceServiceImpl.listAllDevicesWaitingForActivation()).thenReturn(list);
		
        mockMvc.perform(get("/devices/status/waiting")
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(list.size())))
                .andExpect(jsonPath("$[0].temperature").value(dtoResponse.getTemperature()))
                .andExpect(jsonPath("$[0].deviceStatus").value(dtoResponse.getDeviceStatus().name()))
                .andExpect(jsonPath("$[0].simCardDto.operatorCode").value(dtoResponse.getSimCard().getOperatorCode()))
                .andExpect(jsonPath("$[0].simCardDto.country").value(dtoResponse.getSimCard().getCountry()));
	}
	
	@Test
    public void DeviceService_DeleteDeviceById_ReturnVoid() throws Exception {
		
		doNothing().when(deviceServiceImpl).removeDevice(anyInt());

		MvcResult mvcResult = mockMvc.perform(delete("/devices/{deviceId}",device_1.getDeviceId())
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        	String contentAsString = mvcResult.getResponse().getContentAsString();
        	assertEquals("SUCCESS", contentAsString);
	}
	
	@Test
    public void DeviceService_UpdateDevice_Return_ResponseDTO() throws Exception {
		
		when(deviceServiceImpl.updateDeviceStatus(anyInt(), any(DeviceStatus.class))).thenReturn(dtoResponse);
        mockMvc.perform(patch("/devices/{deviceId}",device_1.getDeviceId())
        		.param("deviceStatus", DeviceStatus.ACTIVE.name())
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").value(dtoResponse.getTemperature()))
                .andExpect(jsonPath("$.deviceStatus").value(dtoResponse.getDeviceStatus().name()))
                .andExpect(jsonPath("$.simCardDto.operatorCode").value(dtoResponse.getSimCard().getOperatorCode()))
                .andExpect(jsonPath("$.simCardDto.country").value(dtoResponse.getSimCard().getCountry()));
		
	}
	
	@Test
    public void DeviceService_listAllDevicesForSale_ReturnsDeviceDto_List() throws Exception {

		List<DeviceDtoResponse> list = List.of(dtoResponse);
		when(deviceServiceImpl.listAllDevicesAvailableForSale()).thenReturn(list);
		
        mockMvc.perform(get("/devices/all-valid-sale")
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(list.size())))
                .andExpect(jsonPath("$[0].temperature").value(dtoResponse.getTemperature()))
                .andExpect(jsonPath("$[0].deviceStatus").value(dtoResponse.getDeviceStatus().name()))
                .andExpect(jsonPath("$[0].simCardDto.operatorCode").value(dtoResponse.getSimCard().getOperatorCode()))
                .andExpect(jsonPath("$[0].simCardDto.country").value(dtoResponse.getSimCard().getCountry()));
	}
}
