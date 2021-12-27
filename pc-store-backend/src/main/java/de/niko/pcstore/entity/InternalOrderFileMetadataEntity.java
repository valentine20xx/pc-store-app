package de.niko.pcstore.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
    private String notes;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "internal_order_file_payload_id")
    private InternalOrderFilePayloadEntity internalOrderFilePayloadEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "internal_order_id")
    private InternalOrderEntity internalOrderEntity;
}