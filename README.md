# AssertJ RxJava
[AssertJ](http://joel-costigliola.github.io/assertj/) assertions for [RxJava](https://github.com/ReactiveX/RxJava) Observables.

This library extends AssertJ core with the aim of providing elegant assertions to test RxJava Observables. The current version of this library only provides assertions aimed at testing "cold" Observables. You will need to transform your Observable into a [`BlockingObservable`](http://reactivex.io/RxJava/javadoc/rx/observables/BlockingObservable.html) before performing any assertion. 

Feedback and contributions are very welcome. 

##Examples

* Assert that completes successfully without emitting any error:
```java
assertThat(observable.toBlocking()).completes();
```
* Assert that completes after emitting a single value "hello":
```java
assertThat(observable.toBlocking())
    .completes()
    .emitsSingleValue("hello");
```
* Assert that completes without emitting any value:
```java
assertThat(observable.toBlocking())
    .completes()
    .emitsNoValues();
```
* Assert that emits an error:
```java
assertThat(observable.toBlocking()).fails();
```
* Assert that emits an error of type `IllegalArgumentException`
```java
assertThat(observable.toBlocking())
    .failsWithError(IllegalArgumentException.class);
```
* Assert that completes after emitting three values that are exactly "a", "b" and "c":
```java
assertThat(observable.toBlocking())
    .completes()
    .listOfValuesEmitted()
    .containsExactly("a", "b", "c");
```
* Assert that fails after emitting "a" and "b":
```java
assertThat(observable.toBlocking())
    .fails()
    .listOfValuesEmitted()
    .containsExactly("a", "b");
```
* Assert that completes after emitting 10 values
```java
assertThat(observable.toBlocking())
   .completes()
   .valuesCountIs(10);
```
## Binaries

__Coming soon to Maven central!__

## License 
```
Copyright (C) 2015 Ribot Ltd.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
