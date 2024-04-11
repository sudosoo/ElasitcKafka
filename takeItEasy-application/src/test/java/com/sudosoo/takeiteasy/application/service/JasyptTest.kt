package com.sudosoo.takeiteasy.application.service

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.jasypt.iv.NoIvGenerator
import org.jasypt.iv.RandomIvGenerator
import org.jasypt.salt.RandomSaltGenerator
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Bean


class JasyptTest(
){
    @Test
    fun encrypt() {

        val encrypted1 = stringEncryptor("test")
        println("ip :" + encrypted1)


    }
    @Bean("jasyptStringEncryptor")
    fun stringEncryptor(value: String?): String {
        val encryptor = PooledPBEStringEncryptor()
        val config = SimpleStringPBEConfig()
        encryptor.setPoolSize(1)
        config.password = "password"
        config.algorithm = "PBEWithMD5AndDES"
        config.keyObtentionIterations = 1000
        config.providerName = "SunJCE"
        config.saltGenerator = RandomSaltGenerator()
        config.ivGenerator = NoIvGenerator()
        config.stringOutputType = "base64"
        encryptor.setConfig(config)
        return encryptor.encrypt(value)
    }

}