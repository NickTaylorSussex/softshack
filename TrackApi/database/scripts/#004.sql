update nicksdb.properties p join nicksdb.inflation i on i.year = p.yearSold set inflatedPrice = ((since/100)+1)*price

/* then add an index to inflatedPrice column */