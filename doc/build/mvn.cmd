mvn versions:set -DnewVersion=1.2.0

mvn clean package -pl tinyurl-generator -am
mvn clean package -pl tinyurl-query -am