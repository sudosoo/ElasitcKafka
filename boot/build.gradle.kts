tasks.bootJar {
    enabled = true
}
tasks.jar {
    enabled = false
}


dependencies {
    implementation(project(":takeItEasy-presentation"))
    implementation(project(":takeItEasy-aop"))

    implementation("org.springframework.boot:spring-boot-starter")
    
    testImplementation("org.springframework.batch:spring-batch-test")
}