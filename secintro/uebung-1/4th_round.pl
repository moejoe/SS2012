#!/bin/perl -w
%map = (
" " => " ",
"a" => "E",
"o" => "T",
"l" => "A",
"f" => "R",
"i" => "I",
"y" => "H",
"x" => "N",
"c" => "O",
"m" => "S",
"s" => "D",
"r" => "U",
"g" => "L",
"t" => "C",
"e" => "M",
"h" => "F",
"v" => "Y",
"d" => "W",
"," => "G",
"n" => "K",
"k" => "B",
"q" => "V",
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