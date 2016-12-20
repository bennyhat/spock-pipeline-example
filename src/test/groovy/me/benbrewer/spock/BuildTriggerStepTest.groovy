package me.benbrewer.spock

import hudson.model.FreeStyleProject
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.junit.ClassRule
import org.junit.Rule
import org.jvnet.hudson.test.BuildWatcher
import org.jvnet.hudson.test.JenkinsRule
import org.jvnet.hudson.test.LoggerRule
import spock.lang.Specification

class BuildTriggerStepTest extends Specification {

    @ClassRule
    public static BuildWatcher buildWatcher = new BuildWatcher()
    @Rule
    public JenkinsRule mockJenkins = new JenkinsRule()
    @Rule
    public LoggerRule logging = new LoggerRule()


    def "upstream project triggers downstream project"() {
        when:
        FreeStyleProject downstreamProject = mockJenkins.createFreeStyleProject("ds")
        WorkflowJob upstreamProject = mockJenkins.jenkins.createProject(WorkflowJob.class, "us")

        upstreamProject.setDefinition(new CpsFlowDefinition("""
           def ds = build 'ds'
           echo "ds.result=\${ds.result} ds.number=\${ds.number}"
        """, true))

        then:
        mockJenkins.assertLogContains(
                "ds.result=SUCCESS ds.number=1",
                mockJenkins.buildAndAssertSuccess(upstreamProject)
        )
        downstreamProject.getBuildByNumber(1).delete()
    }
}
