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
"," => "W",
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