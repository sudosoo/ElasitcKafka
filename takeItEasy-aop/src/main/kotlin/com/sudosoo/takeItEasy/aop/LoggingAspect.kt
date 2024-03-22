package com.sudosoo.takeItEasy.aop

import com.fasterxml.jackson.databind.ObjectMapper
import com.sudosoo.takeItEasy.aop.logging.LogInfo
import com.sudosoo.takeItEasy.aop.logging.RequestApiInfo
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.Any
import kotlin.Exception
import kotlin.Throws
import kotlin.to

@Aspect
@Configuration
class LoggingAspect(private val objectMapper: ObjectMapper) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(LoggingAspect::class.java)
    }

    // 모든 컨트롤러 && NotLogging 어노테이션 미설정 시 로그 수집
    @Pointcut("within(*..*Controller) && !@annotation(com.sudosoo.takeItEasy.application.annotation.NotLogging)")
    fun onRequest() {}


    @Around("onRequest()")
    @Throws(Throwable::class)
    fun requestLogging(joinPoint: ProceedingJoinPoint): Any? {

        // API 요청 정보
        val apiInfo = RequestApiInfo(joinPoint, joinPoint.target.javaClass, objectMapper)

        // 로그 정보
        val logInfo = LogInfo(
            apiInfo.url,
            apiInfo.name,
            apiInfo.method,
            apiInfo.header,
            objectMapper.writeValueAsString(apiInfo.parameters).replace("\\", ""),
            objectMapper.writeValueAsString(apiInfo.body).replace("\\", ""),
            apiInfo.ipAddress
        )

        return try {
            val result = joinPoint.proceed(joinPoint.args)

            // Method가 Get이 아닌 로그만 수집
            if (logInfo.method != "GET") {
                val logMessage = objectMapper.writeValueAsString(mapOf("logInfo" to logInfo))
                logger.info(logMessage)
            }
            result

        } catch (e: Exception) {
            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            val exceptionAsString = sw.toString()

            // 발생 Exception 설정
            logInfo.exception = exceptionAsString
            val logMessage = objectMapper.writeValueAsString(logInfo)
            logger.error(logMessage)

            throw e
        }
    }
}
