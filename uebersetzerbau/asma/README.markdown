Termin
------

Abgabe spätestens am 14. März 2012, 14 Uhr

Angabe
------

Gegeben ist folgende C-Funktion: 

~~~~C
void asma(unsigned long x[])  
{  
  unsigned long carry;  
  carry = x[1] << 63;  
  x[1] = x[1] >> 1;  
  x[0] = (x[0] >> 1)|carry;  
} 
~~~~

Schreiben Sie diese Funktion in Assembler unter Verwendung von rcrq. 
Am einfachsten tun Sie sich dabei wahrscheinlich, wenn Sie eine einfache 
C-Funktion wie :

~~~~C
void asma(unsigned long x[])  
{  
  x[0] = x[0]>>1;  
} 
~~~~

mit z.B. `gcc -O -S` in Assembler übersetzen und sie dann verändern. Dann 
stimmt schon das ganze Drumherum. Die Originalfunktion auf diese Weise zu 
übersetzen ist auch recht lehrreich, aber vor allem, um zu sehen, wie man 
es nicht machen soll. 


Hinweis
-------
Beachten Sie, dass Sie nur dann Punkte bekommen, wenn Ihre Version rcrq 
verwendet und korrekt ist, also bei gleicher (zulässiger) Eingabe das gleiche 
Resultat liefert wie das Original. 

Zum Assemblieren und Linken verwendet man am besten gcc, der Compiler-Treiber 
kümmert sich dann um die richtigen Optionen für as und ld. 

Abgabe
------

Zum angegebenen Termin stehen im Verzeichnis ˜/abgabe/asma die maßgeblichen 
Dateien. Mittels make clean soll man alle von Werkzeugen erzeugten Dateien 
löschen können und make soll eine Datei asma.o erzeugen. Diese Datei soll nur 
die Funktion asma enthalten, keinesfalls main. Diese Funktion soll den 
Aufrufkonventionen gehorchen und wird bei der Prüfung der abgegebenen Programme
mit C-Code zusammengebunden. 
