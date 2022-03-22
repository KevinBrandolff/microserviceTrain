# latam-mastercard-dec-2021
Project to do some code practice with Mastercard Latam's team

## Create the scheleton for a Springboot Project
This includes the minimum pom file, starter class and package structure. 

### Package structure
Every participant will have its own folder (name-surname). Inside the folder you can choose the structure that you prefer.

## MySql Connection
Addition of proper dependencies in the pom file, and the configuration should be done in a yaml (.yaml) file. 
The db should maintain default configs. (localhost:3306)
We will use the same db: LATAM_MC_DEC_21

We will need to create a scripts.txt file in the project where we will place all the scripts of the tables.

## Choose a business concept
Based on that we will build the api. E.g companies, organizations, employees, cars, animals, dogs, etc.

## Development
| # | Task |
| ------ | ------ |
| 1 | Create the table in mysql |
| 2 | Update the scripts.txt file with the script of your table |
| 3 | Create the Entity and repository. We will use Spring Data |
| 4 | Create the API (CRUD operations (Create, read all, read by id, update and delete)) Take into considerations Restful API convention :)|
| 5 | Test every endpoint in the api. The idea is to make an e2e test just mocking the db part |
| 6 | Add validations for the business class. E.g In employee, the name shouldn't be null. Play with diff validations. |
| 7 | Add swagger for documentation. Use the config it's already in the project and check for instance that notNull fields appear with the red star in swagger.|
| 8 | Crud for Projects. The relationship with employees is many-to-many. **Check Many2Many details|
| 9 | Add security based on your profile using user and password with a user table in our schema. |
| 10 | Add different roles and play with privileges in different endpoints |
| 11 | Apply authentication applying Jason Web Tokens (JWTs) |
| 12 | Document your api and security workflow with Plant UML. Inside your folder you should add a new Readme.md file containing the diagrams. |


#### Please, write 1 endpoint and then the test before moving to the next endpoint.

#### Many2Many details:
##### Employes
1. Get all should receive the set of projects. If the employee doesnt have projects, we should return an empty set.
2. Create (all in the same method)
   1. Only employee.
   2. Employee with new projects (these projects dont exist yet).
3. Update (complete as Restful definition). All of this with the same method.
   1. Update employee name without modifying projects.
   2. Update employee with 1 new project.
   3. Update employee removing one project of the list.
   4. Update employee updating the name of 1 project.
4. Delete employee. In the intermediate table there shouldnt be relationship of the employee and the project.
   1. Check that if 2 employees has X project, if i delete 1 employee the other should continue be present with the X project.

## Branching Strategy
We will use master as main branch. We need to create feature branches for developing and then merge to master.
Before merging to master, add me as a reviewer of the **Pull Request**! 

## Developers Local Environment Setup

| Description | Script |
| ------ | ------ |
| Create the database | CREATE DATABASE LATAM_MC_DEC_21; |

## Swagger UI

| Description  | URL                                         |
|--------------|---------------------------------------------|
| Swagger UI   | http://localhost:8080/swagger-ui/index.html |
| Swagger Json | http://localhost:8080/v2/api-docs           |




