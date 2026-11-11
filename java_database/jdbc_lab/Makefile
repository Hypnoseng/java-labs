JAVAC = javac
JAVA  = java
OUT   = out
JDBC ?= /usr/share/java/mysql-connector-j-26.10.0.jar

PKG  ?= src
MAIN = gui.StudentForm
SRC   = $(shell find $(PKG) -name "*.java")

.PHONY: build run clean

build:
	mkdir -p $(OUT)
	$(JAVAC) -d $(OUT) $(SRC)

run: build
	$(JAVA) -cp $(OUT):$(JDBC) $(MAIN)

clean:
	rm -rf $(OUT)
	find . -name "*.class" -delete
