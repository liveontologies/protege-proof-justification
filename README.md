# Protege Proof Justification
![Maven Central Version](https://img.shields.io/maven-central/v/org.liveontologies/protege-proof-justification)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Build status](https://ci.appveyor.com/api/projects/status/1br4by6ncw0wtks3?svg=true)](https://ci.appveyor.com/project/ykazakov/protege-proof-justification)

This is a plug-in that provides the Protege Justification Explanation plug-in with
proof-based justifications by registering as an extension to its justification service.
It defines an extension point to obtain a prover also since it does not compute proofs
by its own but uses the chosen prover instead. The main functionality of this plug-in 
is computing justifications based on proofs and providing the Protege Justification
Explanation plug-in with them.

For further information, see <https://github.com/liveontologies/protege-proof-justification>. 

## Prerequisites

Protege Proof Justification is tested to work with Protégé 5.5.0. It may work 
with other versions of Protégé.

## Installation

To install, place all jar files inside the archive 

	protege-proof-justification-0.0.1-SNAPSHOT.zip 

into the `plugins` folder of the Protege installation.

The plug-in supports Protege Auto Update feature which can be used for
upgrading to newer versions according to the instructions here:

    http://protegewiki.stanford.edu/wiki/EnablePluginAutoUpdate

## Development

To develop extensions to be used with this plugin, use the following Maven dependency:

```
<dependency>
  <groupId>org.liveontologies</groupId>
  <artifactId>protege-proof-justification</artifactId>
  <version>${releasedVersion.version}</version>
</dependency>
```
See [`src/main/resources/plugin.xml`](https://github.com/liveontologies/protege-proof-justification/blob/main/src/main/resources/plugin.xml?raw=true) for the definition of the required extension-points.

To use snapshots versions of this library (if not compiled from sources), please add
the Sonatype OSSRH snapshot repository either to your `pom.xml` or `settings.xml`:
```
<repositories>
  <repository>
    <id>ossrh-snapshots</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
  </repository>
</repositories>
```

## License

Protege Proof Justification is Copyright (c) 2016 - 2024 Live Ontologies Project

All sources of this project are available under the terms of the 
[Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)
(see the file `LICENSE.txt`).