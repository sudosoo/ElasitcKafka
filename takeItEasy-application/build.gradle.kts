
dependencies {
    api(project(":takeItEasy-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.batch:spring-batch-test")
}