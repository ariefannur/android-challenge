# Android Cat Fact Application
## Feature
- Fact Today :
  - Display fact about cat today add image from paxels stock image 
  - Caching data fact to local storage
  - Share fact today to other application
- History: 
  - Show recent fact already opened before
  - Search facts history

## Architecture
- Multi module architecture 
  - `:core:common` -> common data & network module 
  - `:core:fact` -> fact data module : caching and get remote data about fact   
  - `:core:image` -> image data module : caching and get remote data about image
  - `:core:ui` -> common ui dan component module 
  - `:app' -> application module (presentation layer)
- Version catalogue 
  - Use gradle version catalog `gradle/libs.version.toml` for centralize dependency management
- Clean Architecture
  - Use clean architecture split into 3 layer data -> domain (usecase) -> presentation
  - Use repository pattern for caching and data integration
- Unit Test
  - Implement unit test each data module `core:fact` & `core:image` covered repository and use cases
  - Implement unit test for presentation `FactViewModel` and `HistoryViewModel`
- Integrated with Github Action
  - https://github.com/ariefannur/android-challenge-speak-buddy/actions


