-- convert varchar timestamp to mysql date format and order by date
SELECT SYMBOL,SERIES,CLOSE,TIMESTAMP FROM MARKET.TICKER ORDER BY SYMBOL,SERIES,STR_TO_DATE(TIMESTAMP,'%d-%M-%Y') ASC

select count(distinct symbol,series) from market.ticker;

select count(distinct isin) from market.ticker;

select count(distinct symbol,series,isin) from market.ticker;

select * from market.ticker;

select symbol,series,close,timestamp,sma from market.indicator order by symbol,series,str_to_date(timestamp,'%d-%M-%Y') asc;

delete from market.ticker where symbol != "20MICRONS"

truncate table market.indicator;
truncate table market.ticker;
truncate table market.file;

