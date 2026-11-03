JAVAC = javac
JAVA  = java
OUT   = out
PKG  ?= OOP/lesson3/computer_room
MAIN ?= $(subst /,.,$(PKG)).Main
SRC   = $(shell find $(PKG) -name "*.java")

.PHONY: build run clean

build:
	mkdir -p $(OUT)
	$(JAVAC) -d $(OUT) $(SRC)

run: build
	$(JAVA) -cp $(OUT) $(MAIN)

clean:
	rm -rf $(OUT)
	find . -name "*.class" -delete
