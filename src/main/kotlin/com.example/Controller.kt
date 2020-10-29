package com.example

import io.zeebe.client.api.response.WorkflowInstanceEvent
import io.zeebe.spring.client.ZeebeClientLifecycle
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class Controller(
        val zeebeClientLifecycle: ZeebeClientLifecycle
) {

    @GetMapping
    fun start() {
        if (!zeebeClientLifecycle.isRunning) {
            return
        }
        val event: WorkflowInstanceEvent = zeebeClientLifecycle
                .newCreateInstanceCommand()
                .bpmnProcessId("demoProcess")
                .latestVersion()
                .variables("{\"a\": \"" + UUID.randomUUID().toString() + "\"}")
                .send()
                .join()
        Worker.logger.info("started instance for workflowKey='{}', bpmnProcessId='{}', version='{}' with workflowInstanceKey='{}'",
                event.workflowKey, event.bpmnProcessId, event.version, event.workflowInstanceKey)
    }
}