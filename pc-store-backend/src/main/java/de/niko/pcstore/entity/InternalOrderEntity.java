package de.niko.pcstore.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "InternalOrder")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InternalOrderEntity extends DefaultPersistenceObject {
    @JoinColumn(name = "personal_computer_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PersonalComputerEntity personalComputer;

    @JoinColumn(name = "client_data_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ClientDataEntity clientData;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "internal_order_id")
    private Set<InternalOrderFileMetadataEntity> internalOrderFileMetadataEntities;

}
