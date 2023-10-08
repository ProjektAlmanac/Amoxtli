import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	jacoco

	kotlin("jvm") version "1.9.10"
	kotlin("plugin.spring") version "1.9.10"

	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"
  	id("org.sonarqube") version "4.3.1.3277"
	id("org.openapi.generator") version "7.0.1"
}

group = "io.github.projektalmanac"
version = "0.0.1-SNAPSHOT"

sonarqube {
	properties {
		property("sonar.projectKey", "AmoxtliBackend")
		property("sonar.exclusions", "**/generated/**")
	}
}

tasks {
	val jacocoTestReport by getting(JacocoReport::class) {
		reports {
			xml.required.set(true)
		}
	}
}

tasks.test {
	finalizedBy(tasks.named("jacocoTestReport")) // report is always generated after tests run
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach{
	kotlinOptions {
		jvmTarget = "17"
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.boot:spring-boot-dependencies:2.6.2")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")

	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

//	implementation("org.springframework.boot:spring-boot-starter-web")
//	implementation("org.springframework.data:spring-data-commons")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.14")
//	implementation("com.google.code.findbugs:jsr305:3.0.2")
//	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
//	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
//	implementation("org.springframework.boot:spring-boot-starter-validation")
//	implementation("com.fasterxml.jackson.core:jackson-databind")

	implementation("org.hibernate.validator:hibernate-validator")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation ("javax.mail:javax.mail-api:1.6.2")


	testImplementation(kotlin("test"))

	implementation("com.google.apis:google-api-services-books:v1-rev20201021-1.30.10")

}

tasks.withType<Test> {
	useJUnitPlatform()
}


openApiGenerate {
	generatorName = "spring"
	inputSpec = "$rootDir/../api-docs.yml"
	outputDir = "$rootDir"
	groupId = "$group"
	modelNameSuffix = "Dto"

	packageName = "io.github.projektalmanac.amoxtli.backend.generated"
	apiPackage = "io.github.projektalmanac.amoxtli.backend.generated.api"
	modelPackage = "io.github.projektalmanac.amoxtli.backend.generated.model"

	additionalProperties.put("interfaceOnly", true)
	additionalProperties.put("skipDefaultInterface", true)
	additionalProperties.put("configPackage", "io.github.projektalmanac.amoxtli.backend.generated.config")
	additionalProperties.put("hideGenerationTimestamp", true)
}