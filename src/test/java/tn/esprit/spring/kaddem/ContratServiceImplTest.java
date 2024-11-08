package tn.esprit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.spring.kaddem.KaddemApplication;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = KaddemApplication.class)
class ContratServiceImplTest {

    @MockBean
    private ContratRepository contratRepository;

    @MockBean
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ContratServiceImpl contratService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllContrats() {
        List<Contrat> mockContrats = Arrays.asList(new Contrat(), new Contrat());
        when(contratRepository.findAll()).thenReturn(mockContrats);

        List<Contrat> contrats = contratService.retrieveAllContrats();
        assertEquals(2, contrats.size());
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    void testUpdateContrat() {
        Contrat contrat = new Contrat();
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat updatedContrat = contratService.updateContrat(contrat);
        assertNotNull(updatedContrat);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testAddContrat() {
        Contrat contrat = new Contrat();
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat addedContrat = contratService.addContrat(contrat);
        assertNotNull(addedContrat);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testAffectContratToEtudiant() {
        String nomE = "Doe";
        String prenomE = "John";
        Integer idContrat = 1;

        Etudiant etudiant = new Etudiant();
        etudiant.setContrats(new HashSet<>());

        Contrat contrat = new Contrat();
        contrat.setArchive(false);

        when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(idContrat)).thenReturn(contrat);
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat affectedContrat = contratService.affectContratToEtudiant(idContrat, nomE, prenomE);
        assertNotNull(affectedContrat);
        assertEquals(etudiant, affectedContrat.getEtudiant());
        verify(etudiantRepository, times(1)).findByNomEAndPrenomE(nomE, prenomE);
        verify(contratRepository, times(1)).findByIdContrat(idContrat);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testGetChiffreAffaireEntreDeuxDates() {
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + (30L * 24 * 60 * 60 * 1000)); // 1 month later

        Contrat contrat1 = new Contrat();
        contrat1.setSpecialite(Specialite.IA);

        Contrat contrat2 = new Contrat();
        contrat2.setSpecialite(Specialite.CLOUD);

        when(contratRepository.findAll()).thenReturn(Arrays.asList(contrat1, contrat2));

        float result = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);
        assertEquals(700, result, 0.01);
        verify(contratRepository, times(1)).findAll();
    }
}