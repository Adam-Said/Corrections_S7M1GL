compile: 
	mkdir -p TP1RMICommons/bin
	mkdir -p TP1RMIClient/bin
	mkdir -p TP1RMIServer/bin

	javac -d TP1RMICommons/bin/ TP1RMICommons/src/commons/*.java
	javac -d TP1RMIServer/bin/ -cp ./TP1RMICommons/bin/ TP1RMIServer/src/server/*.java
	javac -d TP1RMIClient/bin/ -cp ./TP1RMICommons/bin/ TP1RMIClient/src/client/*.java

server:
	java -cp "TP1RMIServer/bin/:TP1RMICommons/bin/" server.Server

client:
	java -cp "TP1RMIClient/bin:TP1RMICommons/bin/" client.Interface

clean: 
	rm -f TP1RMIServer/bin/*.class
	rm -f TP1RMIClient/bin/*.class
	rm -f TP1RMICommons/bin/*.class