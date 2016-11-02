/* Fourth script to execute */

update forge.properties p join forge.inflation i on i.year = p.yearSold set inflatedPrice = ((since/100)+1)*price

/* Add index based on inflatedPrice column after execution manually. */
