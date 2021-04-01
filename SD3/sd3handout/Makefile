# COS 445 SD3, Spring 2019
# Created by Andrew Wonnacott

all: Simulator.class

sd3.zip: Simulator.java Makefile Ultimatum.java Ultimatum_levelzero.java Ultimatum_smweinberg.java Tournament.java strategies.txt
	zip sd3 Simulator.java Makefile Ultimatum.java Ultimatum_levelzero.java Ultimatum_smweinberg.java Tournament.java strategies.txt README.txt

test: results.csv
	@cat results.csv

results.csv: all strategies.txt
	java -ea Simulator strategies.txt > results.csv

Simulator.class: *.java
	javac -Xlint *.java

#strategies.txt: Ultimatum_*.java
#	@touch strategies.txt
#	@ls | grep -e 'Ultimatum_.*\.java' | sed s/.*Ultimatum_// | sed s/\.java$$// > strategies.txt

clean:
	rm -rf *.class results.csv sd3.zip

# upload: sd3.zip
# 	scp sd3.zip cos445@cycles.cs.princeton.edu:~/../htdocs/cos445/sd3.zip
