package com.example.sampleapp.controller

import com.example.sampleapp.model.Sample
import com.example.sampleapp.service.DecisionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class SampleController(@Autowired
                       val decisionService: DecisionService) {

    @PostMapping("/data")
    fun Sample.createData() {
        decisionService.createData(this)
    }
}