import org.jetbrains.kotlin.ir.builders.primitiveOp1

dependencies {
    api(project(":takeItEasy-application"))
    implementation(project(":takeItEasy-batch"))
//    implementation(project(":takeItEasy-aop"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
}