set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
into outfile 'D:/EDW/bag_type_code_", date_format(now(), '%Y%m%d'), ".csv'
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) formatted_output, 1 as seq
union
select '01|SUITCASE - HARD EXT - NO ZIP' formatted_output, 2 as seq 
union
select '02|SUITCASE - HARD F/A STYLE' formatted_output, 2 as seq 
union
select '03|SUITCASE - HARD CORNER NO ZIP' formatted_output, 2 as seq 
union
select '04|SUITCASE (NO ZIPPER)' formatted_output, 2 as seq 
union
select '05|SOFT BAG (NO ZIPPER)' formatted_output, 2 as seq 
union
select '06|BRIEF CASE (NO ZIPPER)' formatted_output, 2 as seq 
union
select '07|SATCHEL' formatted_output, 2 as seq 
union
select '08|DUFFEL BAG (ARMY)' formatted_output, 2 as seq 
union
select '09|LAUNDRY BAG / DRAWSTRING CLOSE' formatted_output, 2 as seq 
union
select '10|BOX (MULTIPLE ITEMS)' formatted_output, 2 as seq 
union
select '11|TRUNK' formatted_output, 2 as seq 
union
select '12|STORAGE CONTAINER' formatted_output, 2 as seq 
union
select '20|GARMENT BAG' formatted_output, 2 as seq 
union
select '22|SUITCASE - SOFT F/A STYLE' formatted_output, 2 as seq 
union
select '23|SUITCASE - ZIPPER' formatted_output, 2 as seq 
union
select '24|SMALL SUITCASE - ZIPPER' formatted_output, 2 as seq 
union
select '25|SPORTS BAG' formatted_output, 2 as seq 
union
select '26|SHOULDER BAG' formatted_output, 2 as seq 
union
select '27|EXPANDABLE BAG IN HEIGHT' formatted_output, 2 as seq 
union
select '28|MATTED WOVEN RUG' formatted_output, 2 as seq 
union
select '29|BACKPACK' formatted_output, 2 as seq 
union
select '50|HAT BOX' formatted_output, 2 as seq 
union
select '51|COURIER BAG/BOX/PACKAGE' formatted_output, 2 as seq 
union
select '52|SAMPLE/DISPLAY CASE' formatted_output, 2 as seq 
union
select '53|ART/DISPLAY PORTFOLIO' formatted_output, 2 as seq 
union
select '54|TUBE (W/O SPORTING EQUIPMENT)' formatted_output, 2 as seq 
union
select '55|DUTY FREE ARTICLES' formatted_output, 2 as seq 
union
select '56|COSMETIC/BEAUTY CASE' formatted_output, 2 as seq 
union
select '57|KENNEL/PET CONTAINER' formatted_output, 2 as seq 
union
select '58|ICE CHEST/COOLER' formatted_output, 2 as seq 
union
select '59|TOOL/TACKLE BOX' formatted_output, 2 as seq 
union
select '60|FISHING RODS' formatted_output, 2 as seq 
union
select '61|FIREARMS' formatted_output, 2 as seq 
union
select '62|GOLF BAGS/CLUBS' formatted_output, 2 as seq 
union
select '63|BICYCLE' formatted_output, 2 as seq 
union
select '64|SLEEPING BAG/BEDROLL/TENT' formatted_output, 2 as seq 
union
select '65|WIND/SURF/BOOGIE BOARD' formatted_output, 2 as seq 
union
select '66|SKIS/SKI POLES' formatted_output, 2 as seq 
union
select '67|SKI/SNOWBOARD' formatted_output, 2 as seq 
union
select '68|SKI BOOTS/BOOT BAG' formatted_output, 2 as seq 
union
select '69|SPORTING EQUIPMENT (OTHER)' formatted_output, 2 as seq 
union
select '70|STROLLER/PRAM/BABY CARRIAGE' formatted_output, 2 as seq 
union
select '71|CHILD/INFANT CAR SEAT' formatted_output, 2 as seq 
union
select '72|CHILD/INFANT EQUIPMENT (OTHER)' formatted_output, 2 as seq 
union
select '73|FULL SIZE PRAM/BABY CARRIAGE/JOGGER' formatted_output, 2 as seq 
union
select '74|UMBRELLA STROLLER' formatted_output, 2 as seq 
union
select '75|WHEELED SPORTING ITEMS' formatted_output, 2 as seq 
union
select '80|AUDIO EQUIPMENT' formatted_output, 2 as seq 
union
select '81|VIDEO EQUIPMENT' formatted_output, 2 as seq 
union
select '82|COMPUTER EQUIPMENT' formatted_output, 2 as seq 
union
select '83|ELECTRICAL APPLIANCES' formatted_output, 2 as seq 
union
select '84|COMMUNICATION EQUIPMENT' formatted_output, 2 as seq 
union
select '85|MUSICAL INSTRUMENT' formatted_output, 2 as seq 
union
select '89|CAMPING/FOLDING/COLLAPSIBLE CHAIR' formatted_output, 2 as seq 
union
select '90|BAGGAGE TROLLEY' formatted_output, 2 as seq 
union
select '91|WCHR/ORTHOPAEDIC DEVICES' formatted_output, 2 as seq 
union
select '92|SECURITY REMOVED ITEMS' formatted_output, 2 as seq 
union
select '93|SHOPPING BAG' formatted_output, 2 as seq 
union
select '94|WHEEL CHAIR' formatted_output, 2 as seq 
union
select '95|ORTHOPAEDIC DEVICES' formatted_output, 2 as seq 
union
select '96|BEDDING BAG' formatted_output, 2 as seq 
union
select '97|SPECIALIST DIVE BAG/EQUIPMENT' formatted_output, 2 as seq 
union
select '98|BEACH PATIO UMBRELLA' formatted_output, 2 as seq 
union
select '99|ARTICLE NOT SHOWN' formatted_output, 2 as seq 
union

select 'T|64' formatted_output, 3 as seq 

) temp
order by seq, formatted_output");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
