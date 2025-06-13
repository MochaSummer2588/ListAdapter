JUnit version used: 4.12
Hamcrest version used: 1.3
JAR files included in the JUnit/ directory:
- junit-4.12.jar
- hamcrest-core-1.3.jar

All tests can be compiled and run from command line using these libraries.
No build tools like Maven or Gradle were used.


COMANDO PER COMPILARE I TEST:
javac -cp "JUnit\junit-4.13.2.jar;JUnit\hamcrest-core-1.3.jar" -d bin myAdapter\*.java myTest\*.java myExceptions\*.java

COMANDO PER ESEGUIRE I TEST:
java -cp "bin;JUnit\junit-4.13.2.jar;JUnit\hamcrest-core-1.3.jar" myTest.TestRunner

COMANDO PER COMPILARE SOLO IL MAIN:
javac -d bin .\myAdapter\*.java .\myExceptions\*.java

COMANDO PER ESEGUIRE SOLO IL MAIN:
java .\myAdapter\MainProva.java

// --- nomeTest() ---
    /*
     * <p><b>Summary:</b> <p>
     * <p><b>Test Case Design:<b> <p>
     * <p><b>Test Description:<b> <p>
     * <p><b>Preconditions:<b> <p>
     * <p><b>Postconditions:<b> <p>
     * <p><b>Expected Result:<b> <p>
     */