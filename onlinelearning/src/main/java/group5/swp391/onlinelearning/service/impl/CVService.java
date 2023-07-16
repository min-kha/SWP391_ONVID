package group5.swp391.onlinelearning.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.CV;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.repository.CVRepository;
import group5.swp391.onlinelearning.service.ICVService;

@Service
public class CVService implements ICVService {

    @Autowired
    CVRepository cVRepository;

    public List<CV> getCVs() {
        return cVRepository.findAll();
    }

    @Override
    public void addCV(@NotNull CV cV) throws Exception {
        if (cVRepository.findById(cV.getId()).isPresent()) {
            throw new InvalidInputException("", "cV.duplicate", "CV is already exists");
        }
        cVRepository.save(cV);
    }

    @Override
    public void deleteCV(int id) {
        cVRepository.deleteById(id);
    }

    @Override
    public void updateCV(@NotNull CV cV) throws Exception {
        Optional<CV> cVTmp = cVRepository.findById(cV.getId());
        if (cVTmp.isPresent()) {
            cVRepository.save(cV);
        } else {
            throw new InvalidInputException("id", "cV.notfound", "CV not found");
        }
    }

    @Override
    public CV getCVById(int id) {
        return cVRepository.findById(id).orElse(null);
    }

}
