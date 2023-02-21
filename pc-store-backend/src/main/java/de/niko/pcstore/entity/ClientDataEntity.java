package de.niko.pcstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ClientData")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ClientDataEntity extends DefaultPersistenceObject {
    @Column
    private String salutation;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String street;
    @Column
    private String houseNumber;
    @Column
    private Integer zip;
    @Column
    private String city;
    @Column
    private String telephone;
    @Column
    private String cellphone;
    @Column
    private String email;
}
