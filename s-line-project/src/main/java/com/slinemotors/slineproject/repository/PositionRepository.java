package com.slinemotors.slineproject.repository;

import com.slinemotors.slineproject.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Integer> {
}
