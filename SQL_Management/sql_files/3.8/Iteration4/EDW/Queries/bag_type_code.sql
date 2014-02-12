set @qry = concat("
#START QUERY

select stuff 

#OUTFILE
into outfile 'D:/EDW/bag_type_code_", date_format(now(), '%Y%m%d'), ".csv'
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) stuff, 1 as seq
union
select '01|SUITCASE - HARD EXT - NO ZIP' stuff, 2 as seq 
union
select '02|SUITCASE - HARD F/A STYLE' stuff, 2 as seq 
union
select '03|SUITCASE - HARD CORNER NO ZIP' stuff, 2 as seq 
union
select '04|SUITCASE (NO ZIPPER)' stuff, 2 as seq 
union
select '05|SOFT BAG (NO ZIPPER)' stuff, 2 as seq 
union
select '06|BRIEF CASE (NO ZIPPER)' stuff, 2 as seq 
union
select '07|SATCHEL' stuff, 2 as seq 
union
select '08|DUFFEL BAG (ARMY)' stuff, 2 as seq 
union
select '09|LAUNDRY BAG / DRAWSTRING CLOSE' stuff, 2 as seq 
union
select '10|BOX (MULTIPLE ITEMS)' stuff, 2 as seq 
union
select '11|TRUNK' stuff, 2 as seq 
union
select '12|STORAGE CONTAINER' stuff, 2 as seq 
union
select '20|GARMENT BAG' stuff, 2 as seq 
union
select '22|SUITCASE - SOFT F/A STYLE' stuff, 2 as seq 
union
select '23|SUITCASE - ZIPPER' stuff, 2 as seq 
union
select '24|SMALL SUITCASE - ZIPPER' stuff, 2 as seq 
union
select '25|SPORTS BAG' stuff, 2 as seq 
union
select '26|SHOULDER BAG' stuff, 2 as seq 
union
select '27|EXPANDABLE BAG IN HEIGHT' stuff, 2 as seq 
union
select '28|MATTED WOVEN RUG' stuff, 2 as seq 
union
select '29|BACKPACK' stuff, 2 as seq 
union
select '50|HAT BOX' stuff, 2 as seq 
union
select '51|COURIER BAG/BOX/PACKAGE' stuff, 2 as seq 
union
select '52|SAMPLE/DISPLAY CASE' stuff, 2 as seq 
union
select '53|ART/DISPLAY PORTFOLIO' stuff, 2 as seq 
union
select '54|TUBE (W/O SPORTING EQUIPMENT)' stuff, 2 as seq 
union
select '55|DUTY FREE ARTICLES' stuff, 2 as seq 
union
select '56|COSMETIC/BEAUTY CASE' stuff, 2 as seq 
union
select '57|KENNEL/PET CONTAINER' stuff, 2 as seq 
union
select '58|ICE CHEST/COOLER' stuff, 2 as seq 
union
select '59|TOOL/TACKLE BOX' stuff, 2 as seq 
union
select '60|FISHING RODS' stuff, 2 as seq 
union
select '61|FIREARMS' stuff, 2 as seq 
union
select '62|GOLF BAGS/CLUBS' stuff, 2 as seq 
union
select '63|BICYCLE' stuff, 2 as seq 
union
select '64|SLEEPING BAG/BEDROLL/TENT' stuff, 2 as seq 
union
select '65|WIND/SURF/BOOGIE BOARD' stuff, 2 as seq 
union
select '66|SKIS/SKI POLES' stuff, 2 as seq 
union
select '67|SKI/SNOWBOARD' stuff, 2 as seq 
union
select '68|SKI BOOTS/BOOT BAG' stuff, 2 as seq 
union
select '69|SPORTING EQUIPMENT (OTHER)' stuff, 2 as seq 
union
select '70|STROLLER/PRAM/BABY CARRIAGE' stuff, 2 as seq 
union
select '71|CHILD/INFANT CAR SEAT' stuff, 2 as seq 
union
select '72|CHILD/INFANT EQUIPMENT (OTHER)' stuff, 2 as seq 
union
select '73|FULL SIZE PRAM/BABY CARRIAGE/JOGGER' stuff, 2 as seq 
union
select '74|UMBRELLA STROLLER' stuff, 2 as seq 
union
select '75|WHEELED SPORTING ITEMS' stuff, 2 as seq 
union
select '80|AUDIO EQUIPMENT' stuff, 2 as seq 
union
select '81|VIDEO EQUIPMENT' stuff, 2 as seq 
union
select '82|COMPUTER EQUIPMENT' stuff, 2 as seq 
union
select '83|ELECTRICAL APPLIANCES' stuff, 2 as seq 
union
select '84|COMMUNICATION EQUIPMENT' stuff, 2 as seq 
union
select '85|MUSICAL INSTRUMENT' stuff, 2 as seq 
union
select '89|CAMPING/FOLDING/COLLAPSIBLE CHAIR' stuff, 2 as seq 
union
select '90|BAGGAGE TROLLEY' stuff, 2 as seq 
union
select '91|WCHR/ORTHOPAEDIC DEVICES' stuff, 2 as seq 
union
select '92|SECURITY REMOVED ITEMS' stuff, 2 as seq 
union
select '93|SHOPPING BAG' stuff, 2 as seq 
union
select '94|WHEEL CHAIR' stuff, 2 as seq 
union
select '95|ORTHOPAEDIC DEVICES' stuff, 2 as seq 
union
select '96|BEDDING BAG' stuff, 2 as seq 
union
select '97|SPECIALIST DIVE BAG/EQUIPMENT' stuff, 2 as seq 
union
select '98|BEACH PATIO UMBRELLA' stuff, 2 as seq 
union
select '99|ARTICLE NOT SHOWN' stuff, 2 as seq 
union

select 'T|64' stuff, 3 as seq 

) temp
order by seq, stuff");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
