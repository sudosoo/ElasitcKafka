dependencies {
    api(project(":takeItEasy-domain"))    //jasypt
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    testImplementation("org.springframework.batch:spring-batch-test")
}