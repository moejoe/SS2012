Termin
------

Abgabe spätestens am 21. März 2012, 14 Uhr

Angabe
------

Gegeben ist folgende C-Funktion: 

~~~~C
#include <stddef.h>  
void asmb(unsigned long x[], size_t n)  
{  
  unsigned long carry=0;  
  unsigned long next_carry;  
  long i;  
  for (i=n-1; i>=0; i--) {  
    next_carry = x[i] <<63;  
    x[i] = (x[i] >> 1) | carry;  
    carry = next_carry;  
  }  
~~~~

Schreiben Sie diese Funktion in Assembler unter Verwendung von rcrq. 

Für besonders effiziente Lösungen (gemessen an der Anzahl der ausgeführten 
Maschinenbefehle; wird ein Befehl n mal ausgeführt, zählt er n-fach) gibt es 
Bonuspunkte. Dabei könnte Ihnen das Wissen helfen, dass `add` und `sub`  das 
Carry-Flag verändern, `inc`, `dec`, `lea` und `mov` dagegen nicht. 





Hinweis
-------
Beachten Sie, dass Sie nur dann Punkte bekommen, wenn Ihre Version korrekt ist,
also bei jeder zulässigen Eingabe das gleiche Resultat liefert wie das 
Original. Dadurch können Sie viel mehr verlieren als Sie durch Optimierung 
gewinnen können, also optimieren Sie im Zweifelsfall lieber weniger als mehr. 
 
Die Vertrautheit mit dem Assembler müssen Sie beim Gespräch am Ende des 
Semesters beweisen, indem Sie Fragen zum abgegebenen Code beantworten. 


Abgabe
------

Zum angegebenen Termin stehen im Verzeichnis `~/abgabe/asmb` die maßgeblichen 
Dateien. Mittels `make clean` soll man alle von Werkzeugen erzeugten Dateien 
löschen können und make soll eine Datei `asma.o` erzeugen. Diese Datei soll nur 
die Funktion asma enthalten, keinesfalls `main`. Diese Funktion soll den 
Aufrufkonventionen gehorchen und wird bei der Prüfung der abgegebenen Programme
mit C-Code zusammengebunden. 
