package de.niko.pcstore.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true, exclude = {"internalOrderFileMetadataEntity"})
@Entity
@Table(name = "InternalOrderFilePayload")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InternalOrderFilePayloadEntity extends DefaultPersistenceObject {
    @Column(nullable = false, length = 10_485_760)
    private byte[] payload;

    @Column(name = "mimetype")
    private String mimeType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "internal_order_file_metadata_id")
    private InternalOrderFileMetadataEntity internalOrderFileMetadataEntity;
}