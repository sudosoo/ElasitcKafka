package com.sudosoo.takeItEasy.application.common

import com.sudosoo.takeItEasy.application.common.DateTime.DateTimeConvert
import java.lang.reflect.InvocationTargetException
import java.time.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties

object ReflectionUtils {
    fun <F : Any, T : Any>overwrite (
        fromSource: F,
        toSource : T,
    ): T {
        val destinationClass = toSource.javaClass

        fromSource::class.memberProperties.forEach { sourceProperty ->
            destinationClass.kotlin.memberProperties
                .find { it.name == sourceProperty.name }
                ?.let { destinationProperty ->
                    if (destinationProperty is KMutableProperty<*>)
                        try {
                            setValueToClass(
                                obj = toSource,
                                fieldName = destinationProperty.name,
                                value = sourceProperty.getter.call(fromSource),
                                valueType = getClassWithName(fromSource, sourceProperty.name)
                            )
                        } catch (e : InvocationTargetException) {
                            // 타입이 상이하여 변환에 실패
                        } catch (e : IllegalArgumentException) {
                            // 타입 미스매치
                        } catch (e : Exception){
                            throw Exception("""
                                reflection error : 클래스 변환에 실패했습니다.
                                ${e.message}
                            """.trimIndent())
                        }
                }
        }
        return toSource
    }

    fun <F : Any, T : Any> convertTo(
        fromSource: F,
        destinationClass: KClass<T>,
    ): T {
        val toSource = destinationClass.createInstance()
        return overwrite(fromSource, toSource)
    }

    fun <F : Any> getValueWithFieldName(obj: F, fieldName: String): Any {
        val field = obj.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(obj)
    }

    inline fun <F : Any, reified V> setValueToClass(
        obj: F,
        fieldName: String,
        value: V,
        valueType: Class<*>,
    ) {
        val field = obj.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true

        if (valueType == field.type) return field.set(obj, value)

        // LocalDate to dateString
        try {
            val dateTime = value as LocalDateTime
            val dateStr = DateTimeConvert.convertToString(dateTime)
            return field.set(obj, dateStr)
        } catch (e : ClassCastException){
            //  pass
        } catch (e : Exception){
            e.printStackTrace()
        }

        // dateString to LocalDate
        try {
            val dateStr = value as String
            val datetime = DateTimeConvert.convertToDateTime(dateStr)
            return field.set(obj, datetime)
        } catch (e : ClassCastException){
            //  pass
        } catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun <F : Any> getClassWithName(obj : F, fieldName: String): Class<*> {
        val field = obj.javaClass.getDeclaredField(fieldName)
        return field.type
    }

}
