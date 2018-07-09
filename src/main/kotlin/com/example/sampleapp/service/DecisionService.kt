package com.example.sampleapp.service

import com.example.sampleapp.model.Sample
import org.springframework.stereotype.Service

@Service
class DecisionService {
    companion object {
        val sampleDb = mutableMapOf(1 to Sample(1, "33aaa", 345.11), 2 to Sample(2, "sdsd222", 232.2332))
    }

    fun createData(sample: Sample) = sampleDb.put(sample.id, sample)
}