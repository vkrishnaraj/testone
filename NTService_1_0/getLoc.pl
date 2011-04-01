use 5.6.1;
use Geo::StreetAddress::US;
use strict;
use warnings;

our $Parser = 'Geo::StreetAddress::US';
my @matches = geocode($ARGV[0]);

sub geocode {
    my ($addr) = @_;
    print "INPUT ADDRESS: $addr\n";
    
    my $part = $Parser->parse_location($addr);

    return unless $part;

    print "OUTPUT ADDRESS: \n";
    print "NUM:" . $part->{number} . "\n" if $part->{number};
    print "PRE:" . $part->{prefix} . "\n" if $part->{prefix};
    print "STR:" . $part->{street} . "\n" if $part->{street};
    print "TYP:" . $part->{type} . "\n" if $part->{type};
    print "SUF:" . $part->{suffix} . "\n" if $part->{suffix};
    print "CIT:" . $part->{city} . "\n" if $part->{city};
    print "STA:" . $part->{state} . "\n" if $part->{state};
    print "ZIP:" . $part->{zip} . "\n" if $part->{zip};
}
