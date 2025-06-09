# SwissRe

### Following below record has been tested

##### Id,firstName,lastName,salary,managerId
###### 1,John,Smith,200000,
###### 2,Alice,Johnson,120000,1
###### 3,Bob,Williams,110000,1
###### 4,Carol,Jones,70000,2
###### 5,David,Brown,75000,2
###### 6,Eve,Davis,72000,3
###### 7,Frank,Miller,71000,3
###### 8,Grace,Wilson,69000,7

# Following result of the program

##### Managers with salary constraint violations:
###### John Smith: salary 200000.00, avg sub salary 115000.00 (should be between 138000.00 and 172500.00)
###### Alice Johnson: salary 120000.00, avg sub salary 72500.00 (should be between 87000.00 and 108750.00)
###### Bob Williams: salary 110000.00, avg sub salary 71500.00 (should be between 85800.00 and 107250.00)
###### Frank Miller: salary 71000.00, avg sub salary 69000.00 (should be between 82800.00 and 103500.00)

