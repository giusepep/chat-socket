all: connection message screen server client

connection:
	javac -d bin/ src/com/chatsocket/connection/*.java

message:
	javac -d bin/ src/com/chatsocket/message/*.java

server:
	javac -cp bin/ -d bin/ src/com/chatsocket/server/*.java

screen:
	javac -cp bin/ -d bin/ src/com/chatsocket/screen/*.java

client:
	javac -cp bin/ -d bin/ src/com/chatsocket/client/*.java

clean:
	rm -rf bin/*

.PHONY: all connectin server screen client clean
