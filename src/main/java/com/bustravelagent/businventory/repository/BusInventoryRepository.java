package com.bustravelagent.businventory.repository;

import com.bustravelagent.businventory.model.BusInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusInventoryRepository extends JpaRepository<BusInventory, Integer>  {

    BusInventory getBusInventoryByBusNo(Integer busNo);
}
