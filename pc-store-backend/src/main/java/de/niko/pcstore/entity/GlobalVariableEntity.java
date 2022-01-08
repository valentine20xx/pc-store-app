package de.niko.pcstore.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "GlobalVariable")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GlobalVariableEntity extends DefaultPersistenceObject {
    @Column(updatable = false, nullable = false)
    private String type;
    @Column(nullable = false)
    private String subtype;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column(nullable = false)
    private Boolean deletable;
}