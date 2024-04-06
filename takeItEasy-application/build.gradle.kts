dependencies {
    api(project(":takeItEasy-domain"))

    testImplementation("org.springframework.batch:spring-batch-test")

    //fixture monkey
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter-kotlin:1.0.15")

}