package de.niko.pcstore.bpm;


import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component("processStart")
public class ProcessStart implements ExecutionListener {
    private static Logger LOGGER = Logger.getLogger(ProcessStart.class.getName());

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        Object variable = delegateExecution.getVariable("order-id");
        if (variable == null) {
            throw new Exception("order-id is null");
        } else if (!(variable instanceof String)) {
            throw new Exception("order-id is not a string");
        }
    }
}
