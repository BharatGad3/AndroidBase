### Usage

Import the module as a library in your project using Gradle:

**root build.gradle**
```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
**your app module build.gradle**
```groovy
dependencies {
//        compile 'com.github.BharatGad3:AndroidBase:master-SNAPSHOT'
}
```
Note: The above line will download the latest version of the module, if you want to run an specific version replace `master-SNAPSHOT` with `1.0.0` or any other version. 

## Features

These features can be found in CORE:

* MVP-ready activities and fragments
* Files helpers
* Camera and image helpers

## <a name="topic-contributing"></a> Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push your branch (`git push origin my-new-feature`)
5. Create a new Pull Request