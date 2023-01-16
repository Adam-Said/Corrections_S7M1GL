
all: compile

compile:
	mkdir -p common/obj
	mkdir -p server/obj
	mkdir -p client/obj
	javac -d common/obj/ -cp common/obj common/src/common/*.java
	javac -d server/obj/ -cp common/obj server/src/Server/*.java
	javac -d client/obj/ -cp common/obj client/src/client/*.java

run-server:
	java -cp "server/obj:common/obj" Server.Server

run-client:
	java -cp "client/obj:common/obj" client.MainClient

run-gui:
	java -cp "client/obj:common/obj" client.GUI_Tester

clean:
	rm -rf client/obj/*
	rm -rf common/obj/*
	rm -rf server/obj/*
	rmdir client/obj/
	rmdir common/obj/
	rmdir server/obj/