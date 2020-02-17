package com.treez.simpleorderservice;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;

import com.treez.simpleorderservice.controller.InventoryAPI;
import com.treez.simpleorderservice.model.Inventory;
import com.treez.simpleorderservice.service.InventoryService;
import com.treez.simpleorderservice.service.impl.InventoryServiceImpl;

import static org.junit.Assert.assertTrue;

import io.swagger.models.HttpMethod;

@SpringBootTest
@AutoConfigureMockMvc
class SimpleorderserviceApplicationTests {
	
	@InjectMocks
	@Spy
	InventoryService inventoryService = new InventoryServiceImpl();
	
	 @MockBean
	   private InventoryAPI inventoryController;
	

    @Test
    public void getInventoryList(){
    	 when(inventoryService.getAllInventories())
    	 .thenReturn((List<Inventory>) Stream
    			 .of(new Inventory(9,"Cannabis Oils","THC oils",50.00,20, null, null, null)).collect(Collectors.toList()));
    	 assertTrue(inventoryService.getAllInventories().size() > 0);  	 
}
    
}
