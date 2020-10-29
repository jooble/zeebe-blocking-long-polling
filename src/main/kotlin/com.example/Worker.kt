package com.example

import io.zeebe.client.api.response.ActivatedJob
import io.zeebe.client.api.response.WorkflowInstanceEvent
import io.zeebe.client.api.worker.JobClient
import io.zeebe.spring.client.ZeebeClientLifecycle
import io.zeebe.spring.client.annotation.ZeebeWorker
import mu.KLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class Worker {

    @ZeebeWorker(type = "foo")
    fun handleFooJob(client: JobClient, job: ActivatedJob) {
        logJob(job)
        client.newCompleteCommand(job.key).send().join()
    }

    private fun logJob(job: ActivatedJob) {
        logger.info(
                "complete job\n>>> [type: {}, key: {}, element: {}, workflow instance: {}]\n{deadline; {}]\n[headers: {}]\n[variables: {}]",
                job.type,
                job.key,
                job.elementId,
                job.workflowInstanceKey,
                Instant.ofEpochMilli(job.deadline),
                job.customHeaders,
                job.variables)
    }

    companion object: KLogging()
}