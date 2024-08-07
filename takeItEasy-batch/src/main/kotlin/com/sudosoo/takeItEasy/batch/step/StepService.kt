package com.sudosoo.takeItEasy.batch.step

import org.springframework.batch.core.Step
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.beans.factory.annotation.Value

interface StepService <T>{
    fun step(): Step
    fun reader (@Value("#{jobParameters[date]}") date :String?): JpaPagingItemReader<T>
    fun writer () : ItemWriter<T>

}