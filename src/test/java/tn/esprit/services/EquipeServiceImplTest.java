package tn.esprit.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.KaddemApplication;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.services.EquipeServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = KaddemApplication.class)
class EquipeServiceImplTest {

    @Mock
    private EquipeRepository equipeRepository;

    @InjectMocks
    private EquipeServiceImpl equipeService;

    @Test
    void testRetrieveAllEquipes() {
        List<Equipe> mockEquipes = Arrays.asList(new Equipe(), new Equipe());
        when(equipeRepository.findAll()).thenReturn(mockEquipes);

        List<Equipe> equipes = equipeService.retrieveAllEquipes();
        assertEquals(2, equipes.size());
        verify(equipeRepository, times(1)).findAll();
    }

    @Test
    void testAddEquipe() {
        Equipe equipe = new Equipe();
        when(equipeRepository.save(equipe)).thenReturn(equipe);

        Equipe addedEquipe = equipeService.addEquipe(equipe);
        assertNotNull(addedEquipe);
        verify(equipeRepository, times(1)).save(equipe);
    }

    @Test
    void testDeleteEquipe() {
        Integer idEquipe = 1;
        Equipe equipe = new Equipe();
        when(equipeRepository.findById(idEquipe)).thenReturn(Optional.of(equipe));

        equipeService.deleteEquipe(idEquipe);
        verify(equipeRepository, times(1)).delete(equipe);
    }

    @Test
    void testRetrieveEquipe() {
        Integer idEquipe = 1;
        Equipe equipe = new Equipe();
        when(equipeRepository.findById(idEquipe)).thenReturn(Optional.of(equipe));

        Equipe retrievedEquipe = equipeService.retrieveEquipe(idEquipe);
        assertNotNull(retrievedEquipe);
        verify(equipeRepository, times(1)).findById(idEquipe);
    }

    @Test
    void testUpdateEquipe() {
        Equipe equipe = new Equipe();
        when(equipeRepository.save(equipe)).thenReturn(equipe);

        Equipe updatedEquipe = equipeService.updateEquipe(equipe);
        assertNotNull(updatedEquipe);
        verify(equipeRepository, times(1)).save(equipe);
    }


}
