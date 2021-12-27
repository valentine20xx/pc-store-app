package de.niko.pcstore.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
//    PSU [Power Supply Unit]
//    Display device, Monitor
//    Operating System [OS]
//    Input Devices, Mouse, Keyboard


//    @JoinColumn(name = "category_id", insertable = false, updatable = false)
//    @ManyToOne(fetch = FetchType.EAGER)
//    private ConstantEntity categoryObject;
//    @Column(name = "category_id")
//    private String categoryId;
}
