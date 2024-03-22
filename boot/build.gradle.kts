import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks {
    val bootJar: BootJar by getting(BootJar::class)
    {
        enabled = true
    }
    val jar by getting(Jar::class) {
        enabled = false
    }
}
dependencies {
    api(project(":takeItEasy-presentation"))
    implementation(project(":takeItEasy-aop"))

    implementation("org.springframework.boot:spring-boot-starter")
    
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.batch:spring-batch-test")
}