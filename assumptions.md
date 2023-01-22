## Assumptions
There are some assumptions inside the application : 

### Business Assumptions
It should allow adding, updating, removing and fetching recipes. Additionally, users should be able to filter
available recipes based on one or more of the following criteria:
1. Whether the dish is vegetarian
2. The number of servings
3. Specific ingredients (either include or exclude)
4. Text search within the instructions.
For example, the API should be able to handle the following search requests:
   • All vegetarian recipes
   • Recipes that can serve 4 persons and have “potatoes” as an ingredient
   • Recipes without “salmon” as an ingredient that has “oven” in the instructions.

### Technical Assumptions
1) The architecture is microservice style.

2) Methodology of development is TDD.

3) The application is developed stateless.

3) The application is developed horizontal scalable.

   

