package de.niko.pcstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
