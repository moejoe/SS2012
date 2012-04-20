#!/bin/perl -w
%map = (
" " => " ",
"a" => "E",
"o" => "T",
"l" => "A",
"f" => "N",
"i" => "I",
"y" => "H",
"x" => "R",
"c" => "O",
"m" => "S",
"s" => "D",
"r" => "C",
"g" => "L",
"t" => "G",
"e" => "F",
"h" => "U",
"v" => "Y",
"d" => "M",
"," => ",",
"n" => "V",
"k" => "B",
"q" => "W",
"b" => "P",
"w" => "X" );

while( <STDIN> ){
	while ($_ =~ /(.)/g) {
    if( exists($map{$1}) ){
    	print $map{$1};    	
  	}
  	else {
  		print "#";
  	}
	}
	print "\n";
}