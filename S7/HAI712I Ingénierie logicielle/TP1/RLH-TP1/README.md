## Comment lancer

1. Installer maven  
2. Compiler avec ``mvn compile``  
3. Tester avec ``mvn test``  
4. Générer le rapport de couverture avec ``mvn clean jacoco:prepare-agent install jacoco:report``. Le rapport sera lisible dans target/site/jacoco/index.html