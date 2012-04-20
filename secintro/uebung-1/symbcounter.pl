#!/bin/perl -w 

my %seen = ();
my $total = 0;

while( <STDIN> ){
	while ($_ =~ /(.)/g) {
    $seen{$1}++;
    $total++;
	}
}

print "symbol | frequency | probability\n";
print "-------|-----------|------------\n";
foreach my $symbol (keys(%seen)) {
	print pack("A6",$symbol) . " | " .  pack("A9", $seen{$symbol} ) . " | " .  (100.00 * $seen{$symbol}/$total) . "\n";
}