# GiftcardShop
Zip Candidate Mobile Challenge: Android

### Tasks

- Have an appropriate implementation for grouped cart items by brand and value
- Update UI & unit tests accordingly
- Create a separate branch for all the above changes

Grouping of cart items is handled in the AddToCartUseCase class inside the Domain layer. The function checks if the cart item already exists in the database, if so the cart item is updated with new a totalPayable value and Quantity value. If not, the new item is added to the database. Composable functions responsible for displaying cart data has been tweaked. 

### 3 New test cases are added.

1. Utility function "incrementQuantity()"
2. `Adding a non-existing cart item to populated database, successfully adds the cart item`()
3. `Adding an existing cart item, updates the existing items in the database with new values`()
