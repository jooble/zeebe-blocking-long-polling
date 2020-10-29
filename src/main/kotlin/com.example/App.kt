package com.example

import io.zeebe.spring.client.EnableZeebeClient
import io.zeebe.spring.client.annotation.ZeebeDeployment
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@ZeebeDeployment(classPathResources = ["demoProcess.bpmn"])
@EnableZeebeClient
@SpringBootApplication
class App


fun main(args: Array<String>) {
    runApplication<App>(*args)
}