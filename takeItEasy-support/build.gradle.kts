
dependencies {
    api(project(":takeItEasy-domain"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    //aop
    implementation("org.springframework.boot:spring-boot-starter-aop")
    //spring batch
    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.batch:spring-batch-test")
}