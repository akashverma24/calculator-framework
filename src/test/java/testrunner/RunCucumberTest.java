package testrunner;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        plugin = {"pretty", "de.monochromata.cucumber.report.PrettyReports:target/pretty-cucumber"},
        tags = {},
        monochrome = true,
        strict = true)
public class RunCucumberTest {

}
