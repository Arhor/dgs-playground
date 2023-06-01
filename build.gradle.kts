plugins {
    id("base")
    id("java-platform")
}

dependencies {
    constraints {
        api("com.google.code.findbugs:jsr305:${property("app.version.findbugs-jsr305")}")
        api("org.mapstruct:mapstruct-processor:${property("app.version.mapstruct")}")
        api("org.mapstruct:mapstruct:${property("app.version.mapstruct")}")
        api("com.ninja-squad:springmockk:${property("app.version.spring-mockk")}")
    }
}

tasks {
    wrapper {
        gradleVersion = project.property("app.version.gradle").toString()
    }

    register<Jar>("stage") {
        dependsOn(":app-client:build", ":app-server:build")

        group = "build"
        description = "Creates composite Jar file including client and server part"

        entryCompression = ZipEntryCompression.STORED

        val serverBuild = zipTree("${project(":app-server").buildDir}/libs/app-server.jar")
        val clientBuild = "${project(":app-client").projectDir}/build/dist"

        from(serverBuild) { into("/") }
        from(clientBuild) { into("/BOOT-INF/classes/static") }

        manifest {
            from({ serverBuild.find { it.name == "MANIFEST.MF" } })
        }
    }
}
