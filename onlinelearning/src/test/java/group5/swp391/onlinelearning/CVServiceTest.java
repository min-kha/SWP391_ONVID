package group5.swp391.onlinelearning;

import group5.swp391.onlinelearning.entity.CV;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.repository.CVRepository;
import group5.swp391.onlinelearning.service.impl.CVService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CVServiceTest {

    @Mock
    private CVRepository cVRepository;

    @InjectMocks
    private CVService cVService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCVs() {
        List<CV> mockCVs = new ArrayList<>();
        mockCVs.add(getRamdomCV());
        mockCVs.add(getRamdomCV());
        when(cVRepository.findAll()).thenReturn(mockCVs);

        List<CV> result = cVService.getCVs();

        assertEquals(mockCVs, result);
    }

    @Test
    public void testAddCV() throws Exception {
        CV cV = getRamdomCV();

        when(cVRepository.findById(cV.getId())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> cVService.addCV(cV));
        verify(cVRepository, times(1)).save(cV);
    }

    @Test
    public void testAddCVDuplicate() throws Exception {
        CV cV = getRamdomCV();

        when(cVRepository.findById(cV.getId())).thenReturn(Optional.of(cV));

        assertThrows(InvalidInputException.class, () -> cVService.addCV(cV));
        verify(cVRepository, never()).save(cV);
    }

    @Test
    public void testDeleteCV() {
        int cvId = 1;

        assertDoesNotThrow(() -> cVService.deleteCV(cvId));
        verify(cVRepository, times(1)).deleteById(cvId);
    }

    @Test
    public void testUpdateCV() throws Exception {
        CV cV = getRamdomCV();

        when(cVRepository.findById(cV.getId())).thenReturn(Optional.of(cV));

        assertDoesNotThrow(() -> cVService.updateCV(cV));
        verify(cVRepository, times(1)).save(cV);
    }

    @Test
    public void testUpdateCVNotFound() {
        CV cV = getRamdomCV();

        when(cVRepository.findById(cV.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidInputException.class, () -> cVService.updateCV(cV));
        verify(cVRepository, never()).save(cV);
    }

    @Test
    public void testGetCVById() {
        int cvId = 1;
        CV mockCV = getRamdomCV();

        when(cVRepository.findById(cvId)).thenReturn(Optional.of(mockCV));

        CV result = cVService.getCVById(cvId);

        assertEquals(mockCV, result);
    }

    public CV getRamdomCV() {
        int randomNumber = new Random().nextInt(100000);
        return CV.builder().pdfLink("pdfLink " + randomNumber + ".pdf")
                .build();
    }
}
