package com.vodafone.warehousemaangement.integrationtest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.vodafone.warehousemaangement.dto.SimCardDto;
import com.vodafone.warehousemaangement.dto.response.DeviceDtoResponse;
import com.vodafone.warehousemaangement.entity.Device;
import com.vodafone.warehousemaangement.entity.SimCard;
import com.vodafone.warehousemaangement.enumeration.DeviceStatus;
import com.vodafone.warehousemaangement.repository.IDeviceRepository;
import com.vodafone.warehousemaangement.repository.ISimCardRepository;
import com.vodafone.warehousemaangement.service.IDeviceService;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class DeviceIntegrationTestController {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private IDeviceService deviceService;

	@Autowired
	private IDeviceRepository deviceRepository;

	@Autowired
	private ISimCardRepository simCardRepository;
	
	SimCard simCard_1;
	Device device_1;
	SimCardDto simCardDto;
	DeviceDtoResponse dtoResponse;

	@BeforeEach
	public void init() {
		simCard_1 = SimCard.builder().country("Egypt").operatorCode(011).build();
		simCardDto = SimCardDto.builder().country("Egypt").operatorCode(011).build();
		device_1 = Device.builder().deviceStatus(DeviceStatus.READY).temperature(40).simCard(simCard_1)
				.build();
		dtoResponse = DeviceDtoResponse.builder().deviceId(1010).deviceStatus(DeviceStatus.READY).temperature(40)
				.simCard(simCardDto).build();
	}

//	@AfterEach
//	public void clear() {
//		simCardRepository.deleteAll();
//		deviceRepository.deleteAll();
//	}
	
	@Test
    public void DeviceService_listAllDevicesWaiting_ReturnsDeviceDto_List() throws Exception {

		List<DeviceDtoResponse> list = deviceService.listAllDevicesWaitingForActivation();
		dtoResponse = list.get(0);
		System.out.println("dtoResponse: "+dtoResponse);
        mockMvc.perform(get("/devices/status/waiting")
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(list.size())))
                .andExpect(jsonPath("$[0].temperature").value(dtoResponse.getTemperature()))
                .andExpect(jsonPath("$[0].deviceStatus").value(dtoResponse.getDeviceStatus().name()))
                .andExpect(jsonPath("$[0].simCard.operatorCode").value(dtoResponse.getSimCard().getOperatorCode()))
                .andExpect(jsonPath("$[0].simCard.country").value(dtoResponse.getSimCard().getCountry()));
	}
	
	@Test
    public void DeviceService_DeleteDeviceById_ReturnVoid() throws Exception {
		
		device_1 = deviceRepository.save(device_1);
		
		MvcResult mvcResult = mockMvc.perform(delete("/devices/{deviceId}",device_1.getDeviceId())
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        	String contentAsString = mvcResult.getResponse().getContentAsString();
        	assertEquals("SUCCESS", contentAsString);
	}
	
	@Test
    public void DeviceService_UpdateDevice_Return_ResponseDTO() throws Exception {
		
		Device device_2 = Device.builder().deviceStatus(DeviceStatus.DEACTIVATED).temperature(40).simCard(simCard_1)
				.build();
		
		device_2 = deviceRepository.save(device_2);
		
        mockMvc.perform(patch("/devices/{deviceId}",device_2.getDeviceId())
        		.param("deviceStatus", DeviceStatus.ACTIVE.name())
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").value(dtoResponse.getTemperature()))
                .andExpect(jsonPath("$.deviceStatus").value(DeviceStatus.ACTIVE.name()))
                .andExpect(jsonPath("$.simCard.operatorCode").value(dtoResponse.getSimCard().getOperatorCode()))
                .andExpect(jsonPath("$.simCard.country").value(dtoResponse.getSimCard().getCountry()));
		
	}
	
	@Test
    public void DeviceService_listAllDevicesForSale_ReturnsDeviceDto_List() throws Exception {

        mockMvc.perform(get("/devices/all-valid-sale")
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].temperature").value(device_1.getTemperature()))
                .andExpect(jsonPath("$[0].deviceStatus").value(device_1.getDeviceStatus().name()))
                .andExpect(jsonPath("$[0].simCard.operatorCode").value(device_1.getSimCard().getOperatorCode()))
                .andExpect(jsonPath("$[0].simCard.country").value(device_1.getSimCard().getCountry()));
	}
	
}
