import org.jetbrains.kotlin.kapt3.base.Kapt.kapt
import org.springframework.boot.gradle.tasks.bundling.BootJar

//val applicationVersion : String by project
val querydslVersion : String by project

plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("kapt")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
}


allprojects {
    group = "com.sudoSoo"
    version = "0.0.1-SNAPSHOT"


    repositories {
        mavenCentral()
        mavenLocal()
        maven ("https://repo.spring.io/release")
        maven ("https://repo.spring.io/milestone/")
        maven ("https:/jitpack.io")
    }

}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

subprojects {

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.0.4")
            mavenBom("com.google.guava:guava-bom:31.1-jre")
            mavenBom("org.jetbrains.kotlin:kotlin-bom:1.7.22")
        }
    }

    tasks.register("prepareKotlinBuildScriptModel"){}

    configurations.all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }

    //자바 모듈
    if (name == "domain") {
        apply(plugin = "java")

        java {
            sourceCompatibility = JavaVersion.VERSION_17
        }

        //코틀린 모듈
    }else{
        apply(plugin = "org.jetbrains.kotlin.jvm")
        apply(plugin = "kotlin-kapt")
        apply(plugin = "kotlin-jpa")
        apply(plugin = "org.jetbrains.kotlin.plugin.allopen")

        dependencies {
            implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
            implementation("org.jetbrains.kotlin:kotlin-reflect")
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            kapt("com.querydsl:querydsl-apt:${querydslVersion}:jakarta")
        }

        configurations {
            compileOnly {
                extendsFrom(configurations.annotationProcessor.get())
            }
        }

        noArg{
            annotation("javax.persistence.Entity")
        }

        allOpen{
            annotation("jakarta.persistence.Entity")
            annotation("jakarta.persistence.Embeddable")
            annotation("jakarta.persistence.MappedSuperclass")
        }

        tasks{
            compileKotlin {
                kotlinOptions.jvmTarget = "17"
                kotlinOptions.freeCompilerArgs = listOf(
                    "-Xopt-in=kotlin.RequiresOptIn",
                    "-XXLanguage:+InlineClasses"
                )
            }

            compileTestKotlin {
                kotlinOptions.jvmTarget = "17"
            }
        }
    }


    tasks {
        val bootJar: BootJar by getting(BootJar::class)
        {
            enabled = false
        }
        val jar by getting(Jar::class) {
            enabled = true
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-devtools")
        implementation("org.springframework.boot:spring-boot-starter-jdbc")
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }


}
