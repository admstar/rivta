package se.skl.rivta.servicedomaintestframework.testsuiteplugin.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class DocumentGeneratorTask extends DefaultTask {

    def dataFile = "data.xml"
    def stylesheet
    def outFile = "${project.buildDir}/TestSuite-Documentation.html"

    @InputFile
    File getDataFile() {
        def file = project.file(dataFile)
        if (!file.exists() || !file.canRead()) {
            throw new InvalidUserDataException("Could not find dataFile at ${file.absolutePath}")
        }
        file
    }

    @InputFile
    File getStylesheet() {
        if (stylesheet != null) {
            def file = project.file(stylesheet)
            if (!file.exists()) {
                throw new InvalidUserDataException("Could not find stylesheet at ${file.absolutePath}")
            }
            if (!file.canRead()) {
                throw new InvalidUserDataException("Could not read stylesheet at ${file.absolutePath}")
            }
            file
        } else {
            def inputStream = getClass().classLoader.getResourceAsStream("ConvertToHtml.xsl")
            def tempFile = new File("${project.buildDir}/ConvertToHtml.xsl")
            if (!tempFile.exists()) {
                tempFile.parentFile.mkdirs()
                tempFile.createNewFile()
                tempFile.append(inputStream)
            }
            tempFile
        }
    }

    @OutputFile
    File getOutFile() {
        def file = project.file(outFile)
        try {
            if (!file.createNewFile()) {
                throw new InvalidUserDataException("Could not create outFile at ${file.absolutePath}")
            }
        } catch (IOException e) {
            throw new InvalidUserDataException("Could not create outFile at ${file.absolutePath}", e)
        }
        if (!file.canWrite()) {
            throw new InvalidUserDataException("Could not write to outFile at ${file.absolutePath}")
        }
        file
    }

    @TaskAction
    def generate() {
        ant.xslt(in: getDataFile(),
                style: getStylesheet(),
                out: getOutFile())
    }

}
