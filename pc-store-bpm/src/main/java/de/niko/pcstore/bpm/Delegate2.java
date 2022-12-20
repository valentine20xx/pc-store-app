package de.niko.pcstore.bpm;

import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component("delegate2")
public class Delegate2 implements TaskListener {
    private static Logger LOGGER = Logger.getLogger(Delegate2.class.getName());

    @Override
    public void notify(DelegateTask delegateTask) {
        LOGGER.info("Delegate1.execute executed");

        //            ResponseEntity responseEntity = restTemplate.getForEntity("http://localhost:8080/internal-order/" + variable, Object.class, new HashMap<>());
//            Object body = responseEntity.getBody();
//
//            LOGGER.info("Delegate1.execute > body:" + body);

//            curl -X 'GET' \
//            'http://localhost:8080/internal-order/update-status?id=67835dbe-4216-4df2-a4e6-9ddf0baaedef&status=checked' \
//            -H 'accept: application/json'

//        Object variable = delegateTask.getVariable("status");
//        if (variable != null) {
//            LOGGER.info("Delegate2.execute executed > variable:" + variable);
//        }
//
//        delegateTask.setVariable("status", "producing");
    }
}
