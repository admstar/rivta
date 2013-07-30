package se.skl.rivta.servicedomaintestframework.testsuiteplugin.tasks

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.assertEquals

import java.awt.print.PrinterJob

/**
 *
 */
class DocumentGeneratorTaskTest {

    DocumentGeneratorTask task
    Project project

    @Before
    void init() {

        project = ProjectBuilder.builder().build()
        task = project.task('generator', type: DocumentGeneratorTask)


        def data = project.file("data.xml")
        IOUtils.copy(getClass().getResourceAsStream("/data.xml"), new FileOutputStream(data))
    }

    @Test
    void testGenerate() {
        task.dataFile = "data.xml"
        task.outFile = "build/testGenerate.html"

        task.generate();

        def result = project.file("${project.buildDir}/testGenerate.html")
        assertEquals(true, result.exists())
    }

    @Test(expected = InvalidUserDataException)
    void testGenerateNoDataFile() {
        task.dataFile = "data1.xml"
        task.outFile = "build/testGenerate.html"

        task.generate();
    }
    @Test(expected = InvalidUserDataException)
    void testGenerateInvalidOutFile() {
        task.dataFile = "data.xml"
        def myFile = project.file('myFile')
        myFile.createNewFile()
        task.outFile = "myFile/testGenerate.html"

        task.generate();
    }
}
