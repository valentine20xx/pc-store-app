package de.niko.pcstore.bpm;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessInstanceWithVariablesImpl;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class ProcessTest {
    private final Logger LOGGER = Logger.getLogger(ProcessTest.class.getName());

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }

    @Test
    public void testMainProcessBPMN() {
        LOGGER.info("testMainProcessBPMN started");

        Mocks.register("processStart", (JavaDelegate) execution -> {
        });
        Mocks.register("checkOrderServiceCall", (JavaDelegate) execution -> {
        });
        Mocks.register("delegate2", (TaskListener) execution -> {
        });

        ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .setJdbcUrl("jdbc:h2:mem:database_user;DB_CLOSE_ON_EXIT=FALSE")
                .setJobExecutorActivate(true)
                .setAuthorizationEnabled(false)
                .setEnablePasswordPolicy(false)
                .buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        repositoryService.createDeployment().name("main-process.bpmn").addClasspathResource("main-process.bpmn").deploy();

        Map<String, Object> variables = new HashMap<>();
        variables.put("order-id", UUID.randomUUID().toString());

        ProcessInstance processInstance = runtimeService.createProcessInstanceByKey("main-process-id")
                .businessKey("testBusinessKey")
                .setVariables(variables)
                .execute();

        Assertions.assertTrue(((ProcessInstanceWithVariablesImpl) processInstance).getExecutionEntity().isActive());
        Assertions.assertFalse(((ProcessInstanceWithVariablesImpl) processInstance).getExecutionEntity().isEnded());

        LOGGER.info("testMainProcessBPMN finished");
    }
}
