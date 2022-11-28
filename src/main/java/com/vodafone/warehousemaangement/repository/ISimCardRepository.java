package com.vodafone.warehousemaangement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vodafone.warehousemaangement.entity.SimCard;

@Repository
public interface ISimCardRepository extends JpaRepository<SimCard, Integer>{
	
}
