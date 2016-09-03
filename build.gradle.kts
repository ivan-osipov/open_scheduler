import org.gradle.jvm.tasks.Jar

val buildNumber = project.properties["buildNumber"]

group = "org.pyjjs"
version = "0.1${if(buildNumber != null) ".$buildNumber" else "-SNAPSHOT"}"

extra["akka_version"] = "2.4.2"
extra["groovy_version"] = "2.4.7"
extra["findbugs_version"] = "3.0.1"
extra["guava_version"] = "19.0"
extra["slf4j_version"] = "1.7.21"
extra["logback_version"] = "1.1.7"
extra["spring_version"] = "4.3.0.RELEASE"
extra["junit_version"] = "4.12"
extra["hamcrest_version"] = "1.3"

buildscript {
    repositories {
        mavenCentral()
        maven {
            setUrl("http://dl.bintray.com/kotlin/kotlin-dev")
        }
    }

    dependencies {
        classpath(kotlinModule("gradle-plugin"))
    }
}

apply {
    plugin("kotlin")
    plugin("java")
    plugin<ApplicationPlugin>()
}

configure<ApplicationPluginConvention> {
    applicationName = "Open Scheduler"
}

configure<JavaPluginConvention> {
    setSourceCompatibility(1.8)
    setTargetCompatibility(1.8)
}

repositories {
    gradleScriptKotlin()
    mavenCentral()
}

dependencies {
    compile(kotlinModule("stdlib"))
    compile("com.typesafe.akka:akka-actor_2.11:${extra["akka_version"]}")
    compile("org.codehaus.groovy:groovy-all:${extra["groovy_version"]}")
    compile("com.google.code.findbugs:jsr305:${extra["findbugs_version"]}")
    compile("com.google.guava:guava:${extra["guava_version"]}")
    compile("org.slf4j:slf4j-api:${extra["slf4j_version"]}")
    compile("ch.qos.logback:logback-classic:${extra["logback_version"]}")
    compile("ch.qos.logback:logback-core:${extra["logback_version"]}")
    compile("org.springframework:spring-beans:${extra["spring_version"]}")
    compile("org.springframework:spring-context:${extra["spring_version"]}")

    testCompile("junit:junit:${extra["junit_version"]}")
    testCompile("org.hamcrest:hamcrest-library:${extra["hamcrest_version"]}")
}

task<Jar>("fatJar") {
    baseName = "${project.name}-all"
    from(configurations.compile.map {
        if (it.isDirectory) return@map it
        else {
            zipTree(it)
        }
    })
    with(tasks.getByName("jar") as Jar)
}

