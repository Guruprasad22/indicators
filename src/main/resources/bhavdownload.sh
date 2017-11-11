#!/bin/bash
#######################################################################################################################
################# Download the bhavcopy for a duration ################################################################
################# Usage : $0 <startDate> <endDate> <target>############################################################
################## example : $0 20160101 20161231 /some/folder ########################################################

stringGen()
{
      export dateString=$(date -d $1 +%d%b%Y | tr 'a-z' 'A-Z')
      export yr=$(date -d $1 +%Y)
      export mnt=$(date -d $1 +%b | tr 'a-z' 'A-Z')
}

dateRangeFunction()
{
      startDate=$1
      endDate=$2
      while [[ $startDate -le $endDate ]]
      do
        echo "date chosen : $startDate"
        stringGen $startDate
        curl  -o cm$dateString$suffix "$url$yr/$mnt/cm$dateString$suffix"
        if [ $? -eq 0 ]; then
        	if [ -f ./cm$dateString$suffix ]; then
                unzip ./cm$dateString$suffix
                rm -f ./cm$dateString$suffix
            fi
        fi

        startDate=$(date -d "$startDate + 1 days" +%Y%m%d)
      done
}

if [ $# -ne 3 ]; then
	echo "usage => bash -x ./bhavdownload.sh 20170101 20170201 <target-directory-in-unix-style>"
	exit 1
fi

export url=https://nse-india.com/content/historical/EQUITIES/
export suffix=bhav.csv.zip
export outPutFile=myfile.zip
dateRangeFunction $1 $2
mv $PWD/*.csv $3
if [ -f $3/output.csv ]; then
	tail -q -n +2 $3/cm*.csv >> $3/output.csv
else
	echo "symbol,series,open,high,low,close,last,prevclose,tottrdqty,tottrdval,timestamp,totaltrades,isin,dummy" > $3/output.csv
	tail -q -n +2 $3/cm*.csv >> $3/output.csv
fi

rm $3/cm*.csv

exit 0

