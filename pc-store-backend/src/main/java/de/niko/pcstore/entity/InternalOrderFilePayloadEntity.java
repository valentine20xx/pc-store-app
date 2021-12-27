package de.niko.pcstore.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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