package com.sudosoo.takeItEasy.batch.step

enum class BatchType {
    HEAVY_CREATE_POST
    ;
    override fun toString(): String {
        return when(this){
            HEAVY_CREATE_POST-> "heavyCreatePost"
        }
    }
}