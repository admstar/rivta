package se.skl.rivta.servicedomaintestframework.testsuiteplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.bundling.Zip
import se.skl.rivta.servicedomaintestframework.testsuiteplugin.tasks.DocumentGeneratorTask

/**
 * Gradle plugin used to create a zip-package of a test-suite.
 * The plugin copies all xml- and html-files, downloads dependencies in the 'support'
 * configuration and generates documentation of the testsuite from the test-data file, the
 * testsuite is finally stored in a zip-archive.
 * The 'support' configuration is created by the plugin, and does not include transitive dependencies.
 */
class TestSuitePlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {
        project.plugins.apply('base')

        def supportConfiguration = project.configurations.create('support')
        supportConfiguration.transitive = false

        def distDir = project.file("${project.buildDir}/dist")
        distDir.mkdirs()

        def copyResourcesTask =  project.tasks.create('copyResources', Copy.class)
        copyResourcesTask.from project.projectDir
        copyResourcesTask.into distDir
        copyResourcesTask.include('**/*.xml', '**/*.html')
        copyResourcesTask.exclude('build', '.gradle')

        def createDocumentTask =  project.tasks.create('createDocument', DocumentGeneratorTask.class)
        createDocumentTask.outFile "${project.buildDir}/dist/TestSuite-Documentation.html"

        def copyDependenciesTask =  project.tasks.create('copyDependencies', Copy.class)
        copyDependenciesTask.from supportConfiguration
        copyDependenciesTask.into distDir


        def archiveTask =  project.tasks.create('createArchive', Zip.class)
        archiveTask.from distDir
        archiveTask.destinationDir project.buildDir

        archiveTask.dependsOn createDocumentTask
        archiveTask.dependsOn copyResourcesTask
        archiveTask.dependsOn copyDependenciesTask

        def assemble = project.tasks.assemble
        assemble.dependsOn archiveTask
    }
}
