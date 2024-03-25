tasks.bootJar {
    enabled = true
}
tasks.jar {
    enabled = false
}

dependencies {
    implementation(project(":takeItEasy-presentation"))
    implementation(project(":takeItEasy-aop"))

//    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.batch:spring-batch-test")
}