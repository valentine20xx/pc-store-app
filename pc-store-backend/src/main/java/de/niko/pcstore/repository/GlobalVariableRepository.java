package de.niko.pcstore.repository;

import de.niko.pcstore.entity.GlobalVariableEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalVariableRepository extends JpaRepository<GlobalVariableEntity, String> {
    @Query("select gve from GlobalVariableEntity as gve where gve.id = :id and gve.dateOfDeletion is null")
    Optional<GlobalVariableEntity> findById(String id);

    @Query("select gve from GlobalVariableEntity as gve where gve.dateOfDeletion is null order by gve.type, gve.name")
    List<GlobalVariableEntity> findAll();

    @Query("select gve from GlobalVariableEntity as gve where gve.type in :types and gve.dateOfDeletion is null order by gve.type, gve.subtype, gve.name")
    List<GlobalVariableEntity> getAllByTypes(List<String> types);
}
