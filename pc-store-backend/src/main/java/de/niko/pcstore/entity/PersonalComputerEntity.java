package de.niko.pcstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "PersonalComputer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PersonalComputerEntity extends DefaultPersistenceObject {
    @Column
    private String computerCase;
    @Column
    private String motherboard;
    @Column
    private String processor;
    @Column
    private String graphicsCard;
    @Column
    private String randomAccessMemory;
    @Column
    private String storageDevice;
    @Column
    private String powerSupplyUnit;
}
