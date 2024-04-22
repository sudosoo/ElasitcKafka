dependencies {
    api(project(":takeItEasy-domain"))    //jasypt
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")

    testImplementation("org.springframework.batch:spring-batch-test")

}