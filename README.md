# WSJ historical stock prices scraper in Java

author: 

## KARLO SKOK
### email: karlo.skok13@gmail.com

JAVA intelliJ IDEA

What program does?
- program download historical stock prices from WSJ Market web page for each company in country list and store it excel file

How program download historical prices?
- for each company in county list program open GUI-less browser and run appropriate url on WSJ market web page, for each company url is created by concatenation of values in columns (WSJ Code, WSJ Prefix) in county list.
- when link is opened, program insert start and end date from SettingsFile to web page and download CSV file of historical stock prices from start date to end date, then saves it in CSFFiles/County folder by company name

How program write results?
- after CSV files are download and saved, for each company programs opens CSV file with historical stock prices search for date that correspond to close date in SettingsFile, if date is there then get's close value and write it to excel file at row of that company name, repeats that steps for each company
- after passes through all companies program saves excel file and program is over


Example -> SettingsFile:
01/01/2020
02/19/2020
02/10/20
Israel, Netherlands, HK


-----------------------
compiled by java version "1.8.0_241"


