JCC = javac
JFLAGS = -g
default: Main.class
Main.class: Main.java SimpleDB.java Transaction.java
	$(JCC) $(JFLAGS) Main.java SimpleDB.java Transaction.java
clean: 
	$(RM) *.class
