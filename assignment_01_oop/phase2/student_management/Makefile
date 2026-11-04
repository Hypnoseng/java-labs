JAVAC = javac
JAVA  = java
OUT   = out
PKG   = assignment_01_oop.phase2.student_management
MAIN  = $(PKG).Main
SRC   = $(shell find . -name "*.java" -not -path "./$(OUT)/*")

.PHONY: build run clean

build:
	mkdir -p $(OUT)
	$(JAVAC) -d $(OUT) $(SRC)

run: build
	$(JAVA) -cp $(OUT) $(MAIN)

clean:
	rm -rf $(OUT)
