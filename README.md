# Log Analyzer
This program will read a log file and then parse it and analyze it so that they are easy to investigate. 
The output will show:
 * The count of lines in the file.
 * The count of each log level (such as INFO,  DEBUG, ERROR, or WARNING).
 * The count of `success` or `pass` lines.
 * The count of `failure`, `fail`, or `failed` lines.

Logs were downloaded from [Apache Access Logs](https://github.com/elastic/examples/tree/master/Common%20Data%20Formats/apache_logs) for the JUnit tests

## How to run it:
* With maven: `mvn clean compile exec:java -Dexec.args="path/to/your/logfile.log"`

Example: `mvn clean compile exec:java -Dexec.args="LogExamples/logExampleWindows.log"`

### **Note: JUnit tests have not been developed yet

