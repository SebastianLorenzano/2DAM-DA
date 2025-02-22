package com.sl2425.da.sellersapp.restapi;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerUpdateDTO;
import com.sl2425.da.sellersapp.restapi.services.SellerServices;
import com.sl2425.da.sellersapp.restapi.services.SellerValidations;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SellersServiceTest
{
    @Mock
    private ISellerEntityDAO sellerDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SellerValidations sellerValidations;

    @InjectMocks
    private SellerServices sellerServices;

    @Test
    void getSellerByCifOk() {
        // Arrange
        String cif = "CIF12345";
        SellerEntity seller = new SellerEntity();
        seller.setCif(cif);
        seller.setName("TestSeller");

        when(sellerDAO.findByCif(cif)).thenReturn(seller);

        // Act
        Pair<Optional<SellerEntity>, LoginCodeStatus> result = sellerServices.getSellerByCif(cif);

        // Assert
        assertEquals(Optional.of(seller), result.getLeft());
        assertEquals(LoginCodeStatus.SUCCESS, result.getRight());
    }

    @Test
    void getSellerByCifNotFound() {
        // Arrange
        String cif = "CIF99999";
        when(sellerDAO.findByCif(cif)).thenReturn(null);

        // Act
        Pair<Optional<SellerEntity>, LoginCodeStatus> result = sellerServices.getSellerByCif(cif);

        // Assert
        assertEquals(Optional.empty(), result.getLeft());
        assertEquals(LoginCodeStatus.CIF_NOT_FOUND, result.getRight());
    }

    @Test
    void updateSellerOk() {
        // Arrange
        SellerUpdateDTO sellerUpdateDTO = new SellerUpdateDTO();
        sellerUpdateDTO.setCif("CIF12345");
        sellerUpdateDTO.setName("UpdatedSeller");
        sellerUpdateDTO.setNewPassword("newPassword");
        sellerUpdateDTO.setConfirmNewPassword("newPassword");

        SellerEntity existingSeller = new SellerEntity();
        existingSeller.setCif("CIF12345");
        existingSeller.setName("OldSeller");

        SellerEntity updatedSeller = new SellerEntity();
        updatedSeller.setCif("CIF12345");
        updatedSeller.setName("UpdatedSeller");

        when(sellerDAO.findByCif("CIF12345")).thenReturn(existingSeller);
        when(sellerValidations.validateSeller(any(SellerEntity.class), any(SellerEntity.class)))
                .thenReturn(new HashSet<>());
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");

        // Act
        Set<SellerCodeStatus> statuses = sellerServices.updateSeller(sellerUpdateDTO);

        // Assert
        assertEquals(1, statuses.size());
        assertEquals(SellerCodeStatus.SUCCESS, statuses.iterator().next());

        // Verify
        verify(sellerDAO, times(1)).save(any(SellerEntity.class));
    }


    @Test
    void updateSellerInvalidData() {
        // Arrange
        SellerUpdateDTO sellerUpdateDTO = new SellerUpdateDTO();
        sellerUpdateDTO.setCif("CIF12345");
        sellerUpdateDTO.setName("UpdatedSeller");
        sellerUpdateDTO.setNewPassword("newPassword");
        sellerUpdateDTO.setConfirmNewPassword("differentPassword"); // Mismatched passwords

        SellerEntity existingSeller = new SellerEntity();
        existingSeller.setCif("CIF12345");
        existingSeller.setName("OldSeller");

        when(sellerDAO.findByCif("CIF12345")).thenReturn(existingSeller);

        // Act
        Set<SellerCodeStatus> statuses = sellerServices.updateSeller(sellerUpdateDTO);

        // Assert
        assertEquals(1, statuses.size());
        assertEquals(SellerCodeStatus.PASSWORDS_DO_NOT_MATCH, statuses.iterator().next());

        // Verify
        verify(sellerDAO, never()).save(any(SellerEntity.class));
    }

    @Test
    void updateSellerNotFound() {
        // Arrange
        SellerUpdateDTO sellerUpdateDTO = new SellerUpdateDTO();
        sellerUpdateDTO.setCif("CIF99999");
        sellerUpdateDTO.setName("NonExistentSeller");

        when(sellerDAO.findByCif("CIF99999")).thenReturn(null);

        // Act
        Set<SellerCodeStatus> statuses = sellerServices.updateSeller(sellerUpdateDTO);

        // Assert
        assertEquals(1, statuses.size());
        assertEquals(SellerCodeStatus.SELLER_NOT_FOUND, statuses.iterator().next());

        // Verify
        verify(sellerDAO, never()).save(any(SellerEntity.class));
    }
}

