package de.niko.pcstore.repository;

import de.niko.pcstore.entity.InternalOrderEntity;
import de.niko.pcstore.entity.InternalOrderFileMetadataEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalOrderRepository extends JpaRepository<InternalOrderEntity, String> {
    @Query("select ioe from InternalOrderEntity as ioe where ioe.dateOfDeletion is null")
    List<InternalOrderEntity> findAll();

    @Query("select ioe from InternalOrderEntity as ioe where ioe.dateOfDeletion is null and ioe.status in :statuses")
    List<InternalOrderEntity> findAllWithStatuses(@Param("statuses") List<InternalOrderEntity.Status> statuses);

    @Query("select ioe from InternalOrderEntity as ioe where ioe.dateOfDeletion is null and ioe.id = :id")
    Optional<InternalOrderEntity> findById(@Param("id") String id);

    @Query("select iofme from InternalOrderFileMetadataEntity as iofme where iofme.dateOfDeletion is null and iofme.id = :fileId")
    InternalOrderFileMetadataEntity getFileMetadata(@Param("fileId") String fileId);
}
