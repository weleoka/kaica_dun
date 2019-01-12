Hej,

För att köra detta program så krävs det helt enkelt att zippa upp filen. Gå sedan til filmappen `standalone/bin`. Där i `/bin` så hittar du två filer: kör i linux med './kaica_dun' eller i Windows med 'kaica_dun.bat' Programmet inkluderar HSQL database som använder filbaserad lagring men stöder SQL dialekt. 

För att inspektera källkoden så är det inkluderat i inlämningen. Ett annat alternativ är att ladda ner en zip, eller alternativt klona, repot på: 

`https://github.com/weleoka/kaica_dun/tree/uppg3`

Detta är en branch som håller just den här kompilerade versionen av spelet. Branchen heter uppg3

För intresserade av development versionen hittar ni källkoden i:
`https://github.com/weleoka/kaica_dun`

Repot har en README.md med fler detaljer om hur man sätter upp en testmiljö. Observera dock att development repot använder en full SQL server istället för HSQLdb, och om du är intresserad av att köra programmet i till exempel IntelliJ eller NetBeans så måste rätt användare och databas skapas i SQL servern Det finns script för detta includerat i repots grundmapp. Du måste givetvis ha MySQL eller MariaDB igång lokalt också. För källkod versionen som är inkluderad i den här inlämningen så kan ni köra den direkt i netbeans eller dyl. Observera att bibliotek osv måste laddas hem innan det kommer fungera. 


Mvh, Kai
