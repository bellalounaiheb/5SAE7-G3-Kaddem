package tn.esprit.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.KaddemApplication;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = KaddemApplication.class)
class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Test
    void testRetrieveAllEtudiants() {
        List<Etudiant> mockEtudiants = Arrays.asList(new Etudiant(), new Etudiant());
        when(etudiantRepository.findAll()).thenReturn(mockEtudiants);

        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();
        assertEquals(2, etudiants.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testAddEtudiant() {
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant addedEtudiant = etudiantService.addEtudiant(etudiant);
        assertNotNull(addedEtudiant);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testUpdateEtudiant() {
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant updatedEtudiant = etudiantService.updateEtudiant(etudiant);
        assertNotNull(updatedEtudiant);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testRetrieveEtudiant() {
        Integer idEtudiant = 1;
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.findById(idEtudiant)).thenReturn(Optional.of(etudiant));

        Etudiant retrievedEtudiant = etudiantService.retrieveEtudiant(idEtudiant);
        assertNotNull(retrievedEtudiant);
        verify(etudiantRepository, times(1)).findById(idEtudiant);
    }

    @Test
    void testRemoveEtudiant() {
        Integer idEtudiant = 1;
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.findById(idEtudiant)).thenReturn(Optional.of(etudiant));

        etudiantService.removeEtudiant(idEtudiant);
        verify(etudiantRepository, times(1)).delete(etudiant);
    }

    @Test
    void testAssignEtudiantToDepartement() {
        Integer etudiantId = 1;
        Integer departementId = 1;
        Etudiant etudiant = new Etudiant();
        Departement departement = new Departement();

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));
        when(departementRepository.findById(departementId)).thenReturn(Optional.of(departement));

        etudiantService.assignEtudiantToDepartement(etudiantId, departementId);

        assertEquals(departement, etudiant.getDepartement());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testAddAndAssignEtudiantToEquipeAndContract() {
        Integer idContrat = 1;
        Integer idEquipe = 1;
        Etudiant etudiant = new Etudiant();
        Contrat contrat = new Contrat();
        Equipe equipe = new Equipe();

        // Initialize the etudiants set to avoid NullPointerException
        equipe.setEtudiants(new HashSet<>());

        when(contratRepository.findById(idContrat)).thenReturn(Optional.of(contrat));
        when(equipeRepository.findById(idEquipe)).thenReturn(Optional.of(equipe));

        Etudiant assignedEtudiant = etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, idContrat, idEquipe);

        assertEquals(etudiant, contrat.getEtudiant());
        assertEquals(1, equipe.getEtudiants().size());
        assertNotNull(assignedEtudiant);
        verify(etudiantRepository, times(0)).save(etudiant); // Transactional, no direct save
    }


    @Test
    void testGetEtudiantsByDepartement() {
        Integer idDepartement = 1;
        List<Etudiant> mockEtudiants = Arrays.asList(new Etudiant(), new Etudiant());

        when(etudiantRepository.findEtudiantsByDepartement_IdDepart(idDepartement)).thenReturn(mockEtudiants);

        List<Etudiant> etudiants = etudiantService.getEtudiantsByDepartement(idDepartement);

        assertEquals(2, etudiants.size());
        verify(etudiantRepository, times(1)).findEtudiantsByDepartement_IdDepart(idDepartement);
    }
}