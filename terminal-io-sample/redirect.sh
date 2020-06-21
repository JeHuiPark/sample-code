cd src
javac `ls`
java Printer > output && java Echo ' redirect' < output
rm -rf `ls *.class`
rm -rf output
