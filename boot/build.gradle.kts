tasks.bootJar {
    enabled = true
}
tasks.jar {
    enabled = false
}

dependencies {
    implementation(project(":takeItEasy-presentation"))
    implementation(project(":takeItEasy-aop"))

    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.batch:spring-batch-test")

    //jasypt
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    //spring batch
    implementation ("org.springframework.boot:spring-boot-starter-batch")

}