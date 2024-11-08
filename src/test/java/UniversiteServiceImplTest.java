import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.KaddemApplication;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;
import tn.esprit.spring.kaddem.services.UniversiteServiceImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = KaddemApplication.class)
class UniversiteServiceImplTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllUniversites() {
        List<Universite> mockUniversites = Arrays.asList(new Universite(), new Universite());
        when(universiteRepository.findAll()).thenReturn(mockUniversites);

        List<Universite> universites = universiteService.retrieveAllUniversites();
        assertEquals(2, universites.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testAddUniversite() {
        Universite universite = new Universite();
        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite addedUniversite = universiteService.addUniversite(universite);
        assertNotNull(addedUniversite);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testUpdateUniversite() {
        Universite universite = new Universite();
        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite updatedUniversite = universiteService.updateUniversite(universite);
        assertNotNull(updatedUniversite);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRetrieveUniversite() {
        Integer idUniversite = 1;
        Universite universite = new Universite();
        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(universite));

        Universite retrievedUniversite = universiteService.retrieveUniversite(idUniversite);
        assertNotNull(retrievedUniversite);
        verify(universiteRepository, times(1)).findById(idUniversite);
    }

    @Test
    void testDeleteUniversite() {
        Integer idUniversite = 1;
        Universite universite = new Universite();
        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(universite));

        universiteService.deleteUniversite(idUniversite);
        verify(universiteRepository, times(1)).delete(universite);
    }

    @Test
    void testAssignUniversiteToDepartement() {
        Integer idUniversite = 1;
        Integer idDepartement = 2;
        Universite universite = new Universite();
        universite.setDepartements(new HashSet<>());

        Departement departement = new Departement();

        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(idDepartement)).thenReturn(Optional.of(departement));

        universiteService.assignUniversiteToDepartement(idUniversite, idDepartement);

        assertEquals(1, universite.getDepartements().size());
        assertEquals(departement, universite.getDepartements().iterator().next());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRetrieveDepartementsByUniversite() {
        Integer idUniversite = 1;
        Universite universite = new Universite();
        Set<Departement> departements = new HashSet<>(Arrays.asList(new Departement(), new Departement()));
        universite.setDepartements(departements);

        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(universite));

        Set<Departement> retrievedDepartements = universiteService.retrieveDepartementsByUniversite(idUniversite);

        assertEquals(2, retrievedDepartements.size());
        verify(universiteRepository, times(1)).findById(idUniversite);
    }
}