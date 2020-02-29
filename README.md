# Online - Calculator Automation

A Behavioral Data Driven approach to test [online-calculator](https://www.online-calculator.com/full-screen-calculator/) application using Java, Selenium and Cucumber. 

# Key Features

 - Use of Online OCR APIs to convert Base64 Image to text.
 - Cucumber Plugin "monochromata" for reporting Features, Scenario and Steps Success and Failure.
 - Reports are present on location "target/pretty-cucumber"
  

## Pre-requisite
```   
 - Java 1.7 or greater  
 - Maven 
 - Cucumber Dependencies
 - Selenium Webdriver 
 - JUnit  
 ```

## Switch to another file

All your files and folders are presented as a tree in the file explorer. You can switch from one to another by clicking a file in the tree.

## Get the code

```  
git clone https://github.com/akashverma24/calculator-framework.git  
cd calculator-framework
```

## Run the code
```
mvn test -DBrowser="Chrome"
supports Firefox using parameter -DBrowser="Firefox"
```