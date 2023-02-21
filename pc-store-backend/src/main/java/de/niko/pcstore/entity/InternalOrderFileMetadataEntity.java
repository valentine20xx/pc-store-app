package de.niko.pcstore.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true, exclude = {"internalOrderEntity", "internalOrderFilePayloadEntity"})
@Entity
@Table(name = "InternalOrderFileMetadata")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InternalOrderFileMetadataEntity extends DefaultPersistenceObject {
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private String note;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "internal_order_file_payload_id")
    private InternalOrderFilePayloadEntity internalOrderFilePayloadEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "internal_order_id")
    private InternalOrderEntity internalOrderEntity;
}