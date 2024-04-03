package com.sudosoo.takeItEasy.batch.step

enum class BatchType {
    HEAVY_CREATE_POST,
    OLD_POSTS_DELETE
    ;
    override fun toString(): String {
        return when(this){
            HEAVY_CREATE_POST-> "heavyCreatePost"
            OLD_POSTS_DELETE -> "oldPostsDelete"
        }
    }
}