package com.sl2425.da.sellersapp.restapi;

import com.sl2425.da.sellersapp.restapi.model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.controllers.api.SellerController;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerLoginDTO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerUpdateDTO;
import com.sl2425.da.sellersapp.restapi.services.SellerServices;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SellersControllerTest {

    @Mock
    private SellerServices sellerServices;

    @InjectMocks
    private SellerController sellerController;

    @Test
    void getSellerByCifAndPasswordOk() {
        // Arrange
        SellerEntity seller = new SellerEntity();
        seller.setCif("CIF12345");
        seller.setName("TestSeller");
        seller.setPassword("827CCB0EEA8A706C4C34A16891F84E7B");
        seller.setPlainPassword("12345");

        SellerLoginDTO s = new SellerLoginDTO();
        s.setCif("CIF12345");
        s.setPassword("12345");

        Pair<Optional<SellerEntity>, LoginCodeStatus> result = Pair.of(Optional.of(seller), LoginCodeStatus.SUCCESS);

        when(sellerServices.getSellerByCifAndPassword(s)).thenReturn(result);

        // Act
        ResponseEntity<?> response = sellerController.getSellerByCifAndPassword(s);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(seller, response.getBody());
    }

    @Test
    void getSellerByCifAndPasswordNotFound() {
        // Arrange
        SellerLoginDTO s = new SellerLoginDTO();
        s.setCif("CIF12345");
        s.setPassword("wrongpassword");

        Pair<Optional<SellerEntity>, LoginCodeStatus> result = Pair.of(Optional.empty(), LoginCodeStatus.INCORRECT_PASSWORD);

        when(sellerServices.getSellerByCifAndPassword(s)).thenReturn(result);

        // Act
        ResponseEntity<?> response = sellerController.getSellerByCifAndPassword(s);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(LoginCodeStatus.INCORRECT_PASSWORD, response.getBody());
    }

    @Test
    void updateSellerCorrectData() {
        // Arrange
        SellerUpdateDTO sellerUpdateDTO = new SellerUpdateDTO();
        sellerUpdateDTO.setCif("CIF12345");
        sellerUpdateDTO.setName("UpdatedSeller");
        Set<SellerCodeStatus> statuses = new HashSet<>();
        statuses.add(SellerCodeStatus.SUCCESS);

        when(sellerServices.updateSeller(sellerUpdateDTO)).thenReturn(statuses);

        // Act
        ResponseEntity<?> response = sellerController.updateSeller(sellerUpdateDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());

        Map<String, Object> expectedBody = new HashMap<>();
        expectedBody.put("message", "Seller updated successfully.");
        assertEquals(expectedBody, response.getBody());
    }

    @Test
    void updateSellerInvalidData() {
        // Arrange
        SellerUpdateDTO sellerUpdateDTO = new SellerUpdateDTO();
        sellerUpdateDTO.setCif("CIF12345");
        sellerUpdateDTO.setName("");  // Invalid data: Empty name

        Set<SellerCodeStatus> statuses = new HashSet<>();
        statuses.add(SellerCodeStatus.INVALID_NAME);

        when(sellerServices.updateSeller(sellerUpdateDTO)).thenReturn(statuses);

        // Act
        ResponseEntity<?> response = sellerController.updateSeller(sellerUpdateDTO);

        // Assert
        assertEquals(400, response.getStatusCodeValue());

        Map<String, Object> expectedBody = new HashMap<>();
        expectedBody.put("message", "Seller update failed.");
        expectedBody.put("errors", statuses);
        assertEquals(expectedBody, response.getBody());
    }

    @Test
    void updateSellerSellerNotFound() {
        // Arrange
        SellerUpdateDTO sellerUpdateDTO = new SellerUpdateDTO();
        sellerUpdateDTO.setCif("CIF99999");  // CIF that doesn't exist
        sellerUpdateDTO.setName("NonExistentSeller");

        Set<SellerCodeStatus> statuses = new HashSet<>();
        statuses.add(SellerCodeStatus.SELLER_NOT_FOUND);

        when(sellerServices.updateSeller(sellerUpdateDTO)).thenReturn(statuses);

        // Act
        ResponseEntity<?> response = sellerController.updateSeller(sellerUpdateDTO);

        // Assert
        assertEquals(400, response.getStatusCodeValue());

        Map<String, Object> expectedBody = new HashMap<>();
        expectedBody.put("message", "Seller update failed.");
        expectedBody.put("errors", statuses);
        assertEquals(expectedBody, response.getBody());
    }
}
