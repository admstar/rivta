package se.skl.rivta.servicedomaintestframework.testsuiteplugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
import se.skl.rivta.servicedomaintestframework.testsuiteplugin.tasks.DocumentGeneratorTask

import static org.junit.Assert.assertEquals

class TestSuitePluginTest {

    @Test
    void testApply() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'test-suite'

        assertEquals(true, project.tasks.createDocument instanceof DocumentGeneratorTask)
    }
}
