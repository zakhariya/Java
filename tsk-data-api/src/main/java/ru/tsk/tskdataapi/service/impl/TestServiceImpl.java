package ru.tsk.tskdataapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsk.tskdataapi.repository.TestRepository;
import ru.tsk.tskdataapi.service.TestService;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository testRepository;

    @Override
    public String getMessage() {
        return testRepository.grr();
    }
}
