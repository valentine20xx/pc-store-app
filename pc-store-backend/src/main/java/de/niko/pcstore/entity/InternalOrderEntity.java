package de.niko.pcstore.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
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

    @Column
    private LocalDate dateOfReceiving;

    @Column
    private Boolean privacyPolicy;

    @Column
    private String createdBy;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        OPEN("open"), CHECKED("checked"), PRODUCING("producing"), PRODUCED("produced"), SENT("sent"), CLOSED("closed");

        private final String status;

        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }

        @JsonCreator
        public static Status fromString(String status) {
            Optional<Status> statusOptional = Arrays.stream(Status.values()).filter(o -> o.status.equalsIgnoreCase(status)).findFirst();

            return statusOptional.orElse(null);
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(status);
        }
    }
}
