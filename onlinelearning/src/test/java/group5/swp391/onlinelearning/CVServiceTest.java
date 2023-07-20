package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import group5.swp391.onlinelearning.entity.CV;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.repository.CVRepository;
import group5.swp391.onlinelearning.service.impl.CVService;
import group5.swp391.onlinelearning.utils.TestDataProvider;

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
        mockCVs.add(TestDataProvider.createSampleCV());
        mockCVs.add(TestDataProvider.createSampleCV());
        when(cVRepository.findAll()).thenReturn(mockCVs);

        List<CV> result = cVService.getCVs();

        assertEquals(mockCVs, result);
    }

    @Test
    public void testAddCV() throws Exception {
        CV cV = TestDataProvider.createSampleCV();

        when(cVRepository.findById(cV.getId())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> cVService.addCV(cV));
        verify(cVRepository, times(1)).save(cV);
    }

    @Test
    public void testAddCVDuplicate() throws Exception {
        CV cV = TestDataProvider.createSampleCV();

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
        CV cV = TestDataProvider.createSampleCV();

        when(cVRepository.findById(cV.getId())).thenReturn(Optional.of(cV));

        assertDoesNotThrow(() -> cVService.updateCV(cV));
        verify(cVRepository, times(1)).save(cV);
    }

    @Test
    public void testUpdateCVNotFound() {
        CV cV = TestDataProvider.createSampleCV();

        when(cVRepository.findById(cV.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidInputException.class, () -> cVService.updateCV(cV));
        verify(cVRepository, never()).save(cV);
    }

    @Test
    public void testGetCVById() {
        int cvId = 1;
        CV mockCV = TestDataProvider.createSampleCV();

        when(cVRepository.findById(cvId)).thenReturn(Optional.of(mockCV));

        CV result = cVService.getCVById(cvId);

        assertEquals(mockCV, result);
    }
}
