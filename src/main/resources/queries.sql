-- convert varchar timestamp to mysql date format and order by date
SELECT SYMBOL,SERIES,CLOSE,TIMESTAMP FROM MARKET.TICKER ORDER BY SYMBOL,SERIES,STR_TO_DATE(TIMESTAMP,'%d-%M-%Y') ASC

