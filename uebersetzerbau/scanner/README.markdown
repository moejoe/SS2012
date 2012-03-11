Termin
------

Abgabe sp�testens am 28. M�rz 2012, 14 Uhr

Angabe
------

Schreiben Sie mit flex einen Scanner, der Identifier, Zahlen, und folgende 
Schl�sselw�rter unterscheiden kann: 

* `end`
* return 
* goto 
* if 
* then 
* var 
* not 
* and

Weiters soll er auch noch folgende Lexeme erkennen: 
*	; 
*	( 
*	) 
*	, 
*	: 
*	= 
*	\* 
* - 
* +
* =< 
* # 

Identifier bestehen aus Buchstaben, Ziffern und _, d�rfen aber nicht mit Ziffern 
beginnen. Zahlen sind entweder Hexadezimalzahlen oder Dezimalzahlen. 
Hexadezimalzahlen beginnen mit einer Dezimalziffer, gefolgt von 
Hexadezimalziffern, wobei Hex-Ziffern sowohl gro� als auch klein geschrieben sein 
d�rfen. Dezimalzahlen beginnen mit & gefolgt von Dezimalziffern. Leerzeichen, 
Tabs und Newlines zwischen den Lexemen sind erlaubt und werden ignoriert, ebenso 
Kommentare, die mit (\* anfangen und bis zum n�chsten \*) gehen; Kommentare 
k�nnen also nicht geschachtelt werden). Alles andere sind lexikalische Fehler. 
Es soll jeweils das l�ngste m�gliche Lexem erkannt werden, if39 ist also ein 
Identifier (longest input match), 3aor ist die Zahl 3a gefolgt vom Schl�sselwort 
or. 

Der Scanner soll f�r jedes Lexem eine Zeile ausgeben: f�r Schl�sselw�rter und 
Lexeme aus Sonderzeichen soll das Lexem ausgegeben werden, f�r Identifier ident 
gefolgt von einem Leerzeichen und dem String des Identifiers, f�r Zahlen num 
gefolgt von einem Leerzeichen und der Zahl in Dezimaldarstellung ohne f�hrende 
Nullen. F�r Leerzeichen, Tabs, Newlines und Kommentare soll nichts ausgegeben 
werden (auch keine Leerzeile). 

Der Scanner soll zwischen Gro�- und Kleinbuchstaben unterscheiden, End ist also 
kein Schl�sselwort. 


Abgabe
------

Legen Sie ein Verzeichnis �/abgabe/scanner an, in das Sie die ma�geblichen 
Dateien stellen. Mittels make clean soll man alle von Werkzeugen erzeugten 
Dateien l�schen k�nnen (auch den ausf�hrbaren Scanner) und mittels make ein 
Programm namens scanner erzeugen, das von der Standardeingabe liest und auf die 
Standardausgabe ausgibt. Korrekte Eingaben sollen akzeptiert werden (Ausstieg 
mit Status 0, z.B. mit exit(0)), bei einem lexikalischen Fehler soll der 
Fehlerstatus 1 erzeugt werden. Bei einem lexikalischen Fehler darf der Scanner 
Beliebiges ausgeben (eine sinnvolle Fehlermeldung hilft bei der Fehlersuche). 
