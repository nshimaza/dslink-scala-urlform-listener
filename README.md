# dslink-scala-urlform-listener
This DSLink listens HTTP POST with application/x-www-form-urlencoded body and
publishes it in JSON form at `Posted` Node under the DSLink.

## Build

```sh-session
./gradlew dist
```

You will get `build/distributions/playBinary.zip`.

## Deploy

1. Stop EFM server.
1. Extract the binary under `dslink` directory of EFM broker.
1. Rename the directory name `playBinary` to anything you like.
1. Start EFM server.
