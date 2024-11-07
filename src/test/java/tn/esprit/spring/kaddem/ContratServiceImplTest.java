package tn.esprit.spring.kaddem.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class ContratServiceImplTest {

    @InjectMocks
    private ContratServiceImpl contratService;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @Test
    void testRetrieveAllContrats() {
        List<Contrat> contrats = Collections.singletonList(new Contrat());
        when(contratRepository.findAll()).thenReturn(contrats);

        List<Contrat> result = contratService.retrieveAllContrats();

        assertEquals(1, result.size());
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    void testAddContrat() {
        Contrat contrat = new Contrat();
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.addContrat(contrat);

        assertNotNull(result);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testUpdateContrat() {
        Contrat contrat = new Contrat();
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.updateContrat(contrat);

        assertNotNull(result);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testRetrieveContrat() {
        Integer idContrat = 1;
        Contrat contrat = new Contrat();
        when(contratRepository.findById(idContrat)).thenReturn(Optional.of(contrat));

        Contrat result = contratService.retrieveContrat(idContrat);

        assertNotNull(result);
        verify(contratRepository, times(1)).findById(idContrat);
    }

    @Test
    void testRemoveContrat() {
        Integer idContrat = 1;
        Contrat contrat = new Contrat();
        when(contratRepository.findById(idContrat)).thenReturn(Optional.of(contrat));

        contratService.removeContrat(idContrat);

        verify(contratRepository, times(1)).delete(contrat);
    }

    @Test
    void testAffectContratToEtudiantWithLessThan4ActiveContracts() {
        Integer idContrat = 1;
        String nomE = "John";
        String prenomE = "Doe";
        Etudiant etudiant = new Etudiant();
        etudiant.setContrats(new HashSet<>());

        Contrat contrat = new Contrat();
        contrat.setArchive(false);

        when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(idContrat)).thenReturn(contrat);
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.affectContratToEtudiant(idContrat, nomE, prenomE);

        assertNotNull(result);
        assertEquals(etudiant, result.getEtudiant());
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testAffectContratToEtudiantWithMaxActiveContracts() {
        Integer idContrat = 1;
        String nomE = "John";
        String prenomE = "Doe";

        Etudiant etudiant = new Etudiant();
        Set<Contrat> contrats = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            Contrat activeContrat = new Contrat();
            activeContrat.setArchive(false);
            contrats.add(activeContrat);
        }
        etudiant.setContrats(contrats);

        Contrat contrat = new Contrat();
        contrat.setArchive(false);

        when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(idContrat)).thenReturn(contrat);

        Contrat result = contratService.affectContratToEtudiant(idContrat, nomE, prenomE);

        assertNull(result.getEtudiant()); // Expecting no assignment if there are already 4 active contracts
        verify(contratRepository, times(0)).save(contrat);
    }


    @Test
    void testNbContratsValides() {
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 10 * 24 * 60 * 60 * 1000); // 10 days later
        Integer expectedValidContracts = 5;

        when(contratRepository.getnbContratsValides(startDate, endDate)).thenReturn(expectedValidContracts);

        Integer result = contratService.nbContratsValides(startDate, endDate);

        assertEquals(expectedValidContracts, result);
        verify(contratRepository, times(1)).getnbContratsValides(startDate, endDate);
    }

    @Test
    void testRetrieveAndUpdateStatusContrat() {
        Contrat contract15DaysOld = new Contrat();
        contract15DaysOld.setArchive(false);
        contract15DaysOld.setDateFinContrat(new Date(System.currentTimeMillis() - 15L * 24 * 60 * 60 * 1000));

        Contrat contractExpired = new Contrat();
        contractExpired.setArchive(false);
        contractExpired.setDateFinContrat(new Date());

        List<Contrat> contrats = Arrays.asList(contract15DaysOld, contractExpired);

        when(contratRepository.findAll()).thenReturn(contrats);
        when(contratRepository.save(any(Contrat.class))).thenReturn(contractExpired);

        contratService.retrieveAndUpdateStatusContrat();

        verify(contratRepository, times(1)).save(contractExpired);
        assertTrue(contractExpired.getArchive());
    }

    @Test
    void testGetChiffreAffaireEntreDeuxDates() {
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 60L * 24 * 60 * 60 * 1000); // 60 days later (approx. 2 months)

        Contrat contrat1 = new Contrat();
        contrat1.setSpecialite(Specialite.IA);

        Contrat contrat2 = new Contrat();
        contrat2.setSpecialite(Specialite.CLOUD);

        List<Contrat> contrats = List.of(contrat1, contrat2);
        when(contratRepository.findAll()).thenReturn(contrats);

        float result = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);

        float expected = (2 * 300) + (2 * 400); // 2 months of IA + CLOUD
        assertEquals(expected, result, 0.01);
        verify(contratRepository, times(1)).findAll();
    }
}