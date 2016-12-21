# spock-pipeline-example

This is a quick example of how to test Jenkins pipeline scripts using Spock. 
Most examples out there (under each plugin's test directory) are using JUnit and are not very readable, so this should help with that.

This works fine with Maven 3.3.9 and Oracle JDK 8 from command line.

For IntelliJ, you need to set your JUnit defaults (or for each test run) to run ```mvn compile test-compile``` before the test run, or you'll get weird URI issues in the plugin manager.

NOTE: adapted from the test setup for the official [Pipeline: Build-step plugin](https://github.com/jenkinsci/pipeline-build-step-plugin)

NOTE: I goofed and forgot to remove the resources from main and test. This SHOULD work without those, but I don't have a hot minute to verify.

NOTE: Finally, I can not get my darn surefire plugin to pick up the GMaven stubs, so I just left my test names as *Test.java instead of *Spec.java
