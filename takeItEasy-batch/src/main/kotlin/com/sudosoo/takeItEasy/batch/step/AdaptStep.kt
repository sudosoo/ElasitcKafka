package com.sudosoo.takeItEasy.batch.step

import org.springframework.batch.core.Step
import org.springframework.stereotype.Component

@Component
class AdaptStep(
    val heavyCreatePost : HeavyCreatePost
){
    fun getStep(batchType: BatchType): Step {
        when(batchType){
            BatchType.HEAVY_CREATE_POST -> return heavyCreatePost.step()
            else -> throw IllegalArgumentException("Invalid step name")
        }
    }

}