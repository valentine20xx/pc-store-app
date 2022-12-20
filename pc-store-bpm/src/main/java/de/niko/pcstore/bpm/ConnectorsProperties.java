package de.niko.pcstore.bpm;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@ConfigurationProperties("connectors")
public class ConnectorsProperties {
    private ConnectorProtocol backendProtocol;
    private String backendHost;
    private Integer backendPort;

    public ConnectorProtocol getBackendProtocol() {
        return backendProtocol;
    }

    public void setBackendProtocol(ConnectorProtocol backendProtocol) {
        this.backendProtocol = backendProtocol;
    }

    public String getBackendHost() {
        return backendHost;
    }

    public void setBackendHost(String backendHost) {
        this.backendHost = backendHost;
    }

    public Integer getBackendPort() {
        return backendPort;
    }

    public void setBackendPort(Integer backendPort) {
        this.backendPort = backendPort;
    }

    public enum ConnectorProtocol {
        HTTP("http"), HTTPS("https");

        private final String value;

        ConnectorProtocol(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static ConnectorProtocol fromString(String status) {
            Optional<ConnectorProtocol> statusOptional = Arrays.stream(ConnectorProtocol.values()).filter(status1 -> status1.value.equalsIgnoreCase(status)).findFirst();

            return statusOptional.orElse(null);
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
