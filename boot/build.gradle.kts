
dependencies {
//    implementation(project(":takeItEasy-support"))
    api(project(":takeItEasy-presentation"))
    implementation("org.springframework.boot:spring-boot-starter")
    
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.batch:spring-batch-test")
}