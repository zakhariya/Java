package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {

    @Query("SELECT s FROM Ship s WHERE (:name is null or s.name LIKE CONCAT('%', :name, '%')) " +
            "AND (:planet is null or s.planet LIKE CONCAT('%', :planet, '%')) " +
            "AND (:shipType is null or s.shipType = :shipType) " +
            "AND (:after is null or s.prodDate >= :after) " +
            "AND (:before is null or s.prodDate <= :before) " +
            "AND (:isUsed is null or s.isUsed = :isUsed) " +
            "AND (:minSpeed is null or s.speed >= :minSpeed) " +
            "AND (:maxSpeed is null or s.speed <= :maxSpeed) " +
            "AND (:minCrewSize is null or s.crewSize >= :minCrewSize) " +
            "AND (:maxCrewSize is null or s.crewSize <= :maxCrewSize) " +
            "AND (:minRating is null or s.rating >= :minRating) " +
            "AND (:maxRating is null or s.rating <= :maxRating) " +
            "")
    List<Ship> findBy(@Param("name") String name, @Param("planet") String planet, @Param("shipType") ShipType shipType,
                      @Param("after") Date after, @Param("before") Date before, @Param("isUsed") Boolean isUsed,
                      @Param("minSpeed") Double minSpeed, @Param("maxSpeed") Double maxSpeed,
                      @Param("minCrewSize") Integer minCrewSize, @Param("maxCrewSize") Integer maxCrewSize,
                      @Param("minRating") Double minRating, @Param("maxRating") Double maxRating,
                      Pageable pageable);

    @Query("SELECT COUNT(s) FROM Ship s WHERE (:name is null or s.name LIKE CONCAT('%', :name, '%')) " +
            "AND (:planet is null or s.planet LIKE CONCAT('%', :planet, '%')) " +
            "AND (:shipType is null or s.shipType = :shipType) " +
            "AND (:after is null or s.prodDate >= :after) " +
            "AND (:before is null or s.prodDate <= :before) " +
            "AND (:isUsed is null or s.isUsed = :isUsed) " +
            "AND (:minSpeed is null or s.speed >= :minSpeed) " +
            "AND (:maxSpeed is null or s.speed <= :maxSpeed) " +
            "AND (:minCrewSize is null or s.crewSize >= :minCrewSize) " +
            "AND (:maxCrewSize is null or s.crewSize <= :maxCrewSize) " +
            "AND (:minRating is null or s.rating >= :minRating) " +
            "AND (:maxRating is null or s.rating <= :maxRating)")
    Integer getShipsCount(@Param("name") String name, @Param("planet") String planet, @Param("shipType") ShipType shipType,
                          @Param("after") Date after, @Param("before") Date before, @Param("isUsed") Boolean isUsed,
                          @Param("minSpeed") Double minSpeed, @Param("maxSpeed") Double maxSpeed,
                          @Param("minCrewSize") Integer minCrewSize, @Param("maxCrewSize") Integer maxCrewSize,
                          @Param("minRating") Double minRating, @Param("maxRating") Double maxRating);
}
