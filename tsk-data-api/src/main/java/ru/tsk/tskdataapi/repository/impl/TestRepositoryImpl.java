package ru.tsk.tskdataapi.repository.impl;

import org.springframework.stereotype.Repository;
import ru.tsk.tskdataapi.repository.TestRepository;

@Repository
public class TestRepositoryImpl implements TestRepository {
    @Override
    public String grr() {
        return "Okey!!";
    }
}
