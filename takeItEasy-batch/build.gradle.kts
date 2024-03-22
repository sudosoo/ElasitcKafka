
dependencies {
    implementation(project(":takeItEasy-domain"))
    implementation(project(":takeItEasy-application"))
    implementation(project(":takeItEasy-presentation"))

    //spring batch
    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.batch:spring-batch-test")

}