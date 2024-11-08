package tn.esprit.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.KaddemApplication;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@SpringBootTest(classes = KaddemApplication.class)
@ActiveProfiles("test")
class DepartementServiceImplTest {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;

    @Test
    void testRetrieveAllDepartements() {
        List<Departement> mockDepartements = Arrays.asList(new Departement(), new Departement());
        when(departementRepository.findAll()).thenReturn(mockDepartements);

        List<Departement> departements = departementService.retrieveAllDepartements();
        assertEquals(2, departements.size());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void testAddDepartement() {
        Departement departement = new Departement();
        when(departementRepository.save(departement)).thenReturn(departement);

        Departement addedDepartement = departementService.addDepartement(departement);
        assertNotNull(addedDepartement);
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    void testUpdateDepartement() {
        Departement departement = new Departement();
        when(departementRepository.save(departement)).thenReturn(departement);

        Departement updatedDepartement = departementService.updateDepartement(departement);
        assertNotNull(updatedDepartement);
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    void testRetrieveDepartement() {
        Integer idDepart = 1;
        Departement departement = new Departement();
        when(departementRepository.findById(idDepart)).thenReturn(Optional.of(departement));

        Departement retrievedDepartement = departementService.retrieveDepartement(idDepart);
        assertNotNull(retrievedDepartement);
        verify(departementRepository, times(1)).findById(idDepart);
    }

    @Test
    void testDeleteDepartement() {
        Integer idDepartement = 1;
        Departement departement = new Departement();
        when(departementRepository.findById(idDepartement)).thenReturn(Optional.of(departement));

        departementService.deleteDepartement(idDepartement);
        verify(departementRepository, times(1)).delete(departement);
    }
}