Recompile & Run:

cd "C..\SmartBankSystem\SmartBankSystem"
rm -Recurse bin -Force
mkdir bin
javac --enable-preview --release 21 -d bin @(Get-ChildItem -Path src -Include *.java -Recurse).FullName
java --enable-preview -cp bin BankingApplication
