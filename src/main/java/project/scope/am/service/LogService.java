package project.scope.am.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.scope.am.model.Log;
import project.scope.am.repository.LogRepository;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public void save(Log log) {
        logRepository.save(log);
    }

    public List<Log> findAll() {
        return logRepository.findAll();
    }

    public void deleteById(int id) {
        logRepository.deleteById(id);
    }
}
