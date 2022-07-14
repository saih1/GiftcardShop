# GiftcardShop
Zip Candidate Mobile Challenge: Android

## Improvements to be made
1. Some of the data conversion logic is inside the Composable functions, conversion should take place in the domain layer. 
2. Rethink separation of responsibility of ViewModels.
3. UI states could be better structured to improve reusability, decoupling of code, and testability.
- State Hoisting 
- [https://developer.android.com/jetpack/compose/state](https://developer.android.com/jetpack/compose/state)
4. Unit Testing of Flow inside the use-cases. Each flow emission should be collected and tested, not just the first or final emission.
- [https://developer.android.com/kotlin/flow/test](https://developer.android.com/kotlin/flow/test)
- Flow API or Turbine (recommended libraries for testing Flow)
