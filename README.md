#AWS Price Api

This small api will give you hourly and monthly prices for amazon web services.

This will enable you to estimae the cost based on current or future use, 
by feeding this api with data from the normal amazon webservice client.

## Applications

* Monitor your current use
* Monitor the cost of a buisness parameter, like the price per batch job processed.
* Use to show the consequense of a planned change

## Disclaimers
Note this is a api for a live pricelist on aws.amazon.com, if they change the json format this api will break until a update is made. 

#Motivation and History

Unfortnaly amazon does not offer such api themself, but they have a series of json files on their own online price lists, 

Theese json files do however not contain the same instance type identifiers as the webservices, 
so this api will contain a mapping

#Completeness

This api quite new and only for my own use, so it does not cover all services as of now. 

Any help is ofcause welcome, please fork me and implement the services you want to cover.

## Current version covers

* EC2
** Instance types prices
** Volume prices
** Volume Snapshot prices
** Load balancer prices

* RDS
* Mysql standard prices
* Mysql multiaz prices

## On my current todo list

* S3 prices
* More rds types


