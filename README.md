# FetchBarGame

## Running Application

This test automation was composed in IntelliJ (Ultimate Edition) using Java 11 and JUnit. It is required that the user
has Java 11 or higher installed along with an IDE (such as intelliJ) to run the tests on. The required dependencies (
Selenium, Webdrivermanager, JUnit, and logback) are in the pom.xml file and should be handled by Maven (install if not
installed already). Due to the Selenium and Webdriver dependencies, a Chrome browser must be installed. The test file
can be found at /src/test/java/AlgorithmTest.Java

## The Algorithm

The algorithm for this test splits the 9 bars into 3 groups of 3. The first weighing will compare 2 of those groups. By
measuring just those 2 groups, we will be able to determine which of the 3 groups is the lightest.

In the second weighing, the first 2 bars in the lightest group are weighed. The result of that weighing will tell us
what the lightest bar in that group is. The test will then click on the result of the second weighing.

The algorithm guarantees finding the lightest after the second weighing.