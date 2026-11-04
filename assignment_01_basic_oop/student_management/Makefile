JAVAC = javac
JAVA  = java
OUT   = out
PKG   = assignment_01_basic_oop.student_management

MAIN     = $(PKG).Main
GUI_MAIN = $(PKG).src.gui.GuiMain

# Works on JDK 25+ (java.lang.IO is final in 25, no preview flags needed)
FLAGS =

SRC   = $(shell find . -name "*.java" -not -path "./$(OUT)/*")

.PHONY: build run run-gui clean

build:
	mkdir -p $(OUT)
	$(JAVAC) $(FLAGS) -d $(OUT) $(SRC)

# Run Console Application
run: build
	$(JAVA) -cp $(OUT) $(MAIN)

# Run GUI Application
run-gui: build
	$(JAVA) -cp $(OUT) $(GUI_MAIN)

clean:
	rm -rf $(OUT)
