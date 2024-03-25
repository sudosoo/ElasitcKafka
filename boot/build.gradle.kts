import org.springframework.boot.gradle.tasks.bundling.BootJar
tasks {
    withType<BootJar> {
        enabled = true
    }
    withType<Jar>{
        enabled = false
    }
}
dependencies {
    api(project(":takeItEasy-presentation"))
    implementation(project(":takeItEasy-aop"))

    implementation("org.springframework.boot:spring-boot-starter")
    
    testImplementation("org.springframework.batch:spring-batch-test")
}