package com.vodafone.warehousemaangement.unittest.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.vodafone.warehousemaangement.entity.Device;
import com.vodafone.warehousemaangement.entity.SimCard;
import com.vodafone.warehousemaangement.enumeration.DeviceStatus;
import com.vodafone.warehousemaangement.repository.IDeviceRepository;
import com.vodafone.warehousemaangement.repository.ISimCardRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DeviceRepositoryUnitTest {

	@Autowired
	private IDeviceRepository deviceRepository;
	@Autowired
	private ISimCardRepository simCardRepository; 
	
	@Test
	public void DeviceRepository_SaveAll_ReturnSavedDevice() {

		SimCard simCard = SimCard.builder().country("Egypt").operatorCode(011).build();
		simCard = simCardRepository.save(simCard);
		Device device = Device.builder().deviceStatus(DeviceStatus.WAITING_FOR_ACTIVATION).temperature(40).simCard(simCard).build();
		
		
		Device deviceSaved = deviceRepository.save(device);

		// Assert
		Assertions.assertThat(deviceSaved).isNotNull();
		Assertions.assertThat(deviceSaved.getDeviceId()).isGreaterThan(0);

	}

	@Test
	public void DeviceRepository_findByDeviceStatus_ReturnMoreThanOneDevice() {

		List<Device> list = deviceRepository.findByDeviceStatus(DeviceStatus.ACTIVE);
		System.out.println(list);
		Assertions.assertThat(list).isNotNull();
		Assertions.assertThat(list.size()).isEqualTo(2);

	}

	@Test
    public void deviceRepository_findByDeviceStatusAndTemperatureBetween() {

		SimCard simCard_1 = SimCard.builder().country("Egypt").operatorCode(011).build();
		simCard_1 = simCardRepository.save(simCard_1);
		Device device_1 = Device.builder().deviceStatus(DeviceStatus.READY).temperature(40).simCard(simCard_1).build();

		deviceRepository.save(device_1);

		SimCard simCard_2 = SimCard.builder().country("Italy").operatorCode(010).build();
		simCard_2 = simCardRepository.save(simCard_2);
		Device device_2 = Device.builder().deviceStatus(DeviceStatus.READY).temperature(50).simCard(simCard_2).build();

		deviceRepository.save(device_2);

		List<Device> list = deviceRepository.findByDeviceStatusAndTemperatureBetween(DeviceStatus.ACTIVE,-25,85);
		
		Assertions.assertThat(list).isNotNull();
		Assertions.assertThat(list.size()).isEqualTo(2);
		
    }
	
	@Test
    public void deviceRepository_Update_Device() {

		SimCard simCard_1 = SimCard.builder().country("Egypt").operatorCode(011).build();
		simCard_1 = simCardRepository.save(simCard_1);
		Device device_1 = Device.builder().deviceStatus(DeviceStatus.WAITING_FOR_ACTIVATION).temperature(40).simCard(simCard_1).build();

		Device deviceSaved = deviceRepository.save(device_1);

		deviceSaved.setDeviceStatus(DeviceStatus.READY);
		
		Device deviceUpdated = deviceRepository.save(device_1);

		Assertions.assertThat(deviceUpdated).isNotNull();
		Assertions.assertThat(deviceUpdated.getDeviceId()).isGreaterThan(0);
		assertEquals(DeviceStatus.READY, deviceUpdated.getDeviceStatus());
    }
	
	@Test
    public void deviceRepository_Product_Delete_ReturnDeviceIsEmpty() {

		SimCard simCard_1 = SimCard.builder().country("Egypt").operatorCode(011).build();
		simCard_1 = simCardRepository.save(simCard_1);
		Device device_1 = Device.builder().deviceStatus(DeviceStatus.READY).temperature(40).simCard(simCard_1).build();

		Device deviceSaved = deviceRepository.save(device_1);

		deviceRepository.deleteById(deviceSaved.getDeviceId());
        Optional<Device> userDataOptional = deviceRepository.findById(deviceSaved.getDeviceId());

        Assertions.assertThat(userDataOptional).isEmpty();
    }

}
