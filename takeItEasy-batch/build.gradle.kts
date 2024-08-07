
dependencies {
    implementation(project(":takeItEasy-application"))

    //spring batch
    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.batch:spring-batch-test")
}