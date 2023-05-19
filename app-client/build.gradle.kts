@file:Suppress("UNUSED_VARIABLE")

import com.github.gradle.node.npm.task.NpmTask
import com.github.gradle.node.npm.task.NpxTask

plugins {
    id("com.github.node-gradle.node")
    id("idea")
}

node {
    version.set(project.property("app.version.node").toString())

    download.set(System.getenv("DOWNLOAD_NODE") != "false")

    workDir.set(file("${rootDir}/.gradle/nodejs"))
    npmWorkDir.set(file("${rootDir}/.gradle/npm"))
    yarnWorkDir.set(file("${rootDir}/.gradle/yarn"))
}

idea {
    module {
        excludeDirs.addAll(
            files(
                "$projectDir/coverage",
                "$projectDir/build",
                "$projectDir/node_modules",
            )
        )
    }
}

tasks {
    val synchronizeSchema by registering(NpmTask::class) {
        dependsOn(npmInstall)

        workingDir.fileValue(projectDir)

        val schemaPath = "../app-server/src/main/resources/schema/"

        inputs.dir(schemaPath)
        inputs.dir("src")
        inputs.dir("node_modules")
        inputs.file("codegen.ts")

        outputs.dir("src/gql")
        outputs.cacheIf { true }

        args.addAll("run", "codegen", "--", "--schema-path=${schemaPath}")
    }

    val updateBrowserList by registering(NpxTask::class) {
        dependsOn(npmInstall)

        workingDir.fileValue(projectDir)
        command.set("browserslist@latest")

        args.addAll("--update-db")
    }

    val test by registering(NpmTask::class) {
        dependsOn(npmInstall, updateBrowserList, synchronizeSchema)

        group = "verification"
        workingDir.fileValue(projectDir)

        args.addAll("run", "test")
    }

    val build by registering(NpmTask::class) {
        dependsOn(npmInstall, updateBrowserList, synchronizeSchema, test)

        group = "build"
        workingDir.fileValue(projectDir)

        inputs.dir("src")
        inputs.dir("node_modules")
        inputs.files(
            "index.html",
            "package.json",
            "package-lock.json",
            "tsconfig.json",
            "tsconfig.node.json",
            "vite.config.json",
        )

        outputs.dir("build/dist")
        outputs.cacheIf { true }

        args.addAll("run", "build")
    }

    val clean by registering(Delete::class) {
        group = "build"

        delete("build")
    }
}
