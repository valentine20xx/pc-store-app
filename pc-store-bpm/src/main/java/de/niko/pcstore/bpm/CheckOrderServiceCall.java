package de.niko.pcstore.bpm;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("checkOrderServiceCall")
public class CheckOrderServiceCall implements JavaDelegate {
    private static Logger LOGGER = Logger.getLogger(CheckOrderServiceCall.class.getName());

    private ConnectorsProperties connectorsProperties;

    public CheckOrderServiceCall(ConnectorsProperties connectorsProperties) {
        this.connectorsProperties = connectorsProperties;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Object orderId = delegateExecution.getVariable("order-id");

        if (orderId != null) {
            LOGGER.info("Delegate1.execute > orderId:" + orderId);

            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> map = new HashMap<>();
            map.put("id", orderId);
            map.put("status", "checked");

            ConnectorsProperties.ConnectorProtocol backendProtocol = connectorsProperties.getBackendProtocol();
            String backendHost = connectorsProperties.getBackendHost();
            Integer backendPort = connectorsProperties.getBackendPort();

            ResponseEntity<Object> responseEntity = restTemplate.getForEntity(backendProtocol + "://" + backendHost + ":" + backendPort + "/internal-order/update-status?id={id}&status={status}", Object.class, map);
            Object body = responseEntity.getBody();
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                LOGGER.info("Status updated");
            }
        }
    }
}
