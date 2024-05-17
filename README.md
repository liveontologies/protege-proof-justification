# Protege Proof Justification
![Maven Central Version](https://img.shields.io/maven-central/v/org.liveontologies/protege-proof-justification)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Build status](https://ci.appveyor.com/api/projects/status/1br4by6ncw0wtks3?svg=true)](https://ci.appveyor.com/project/ykazakov/protege-proof-justification)

A plug-in for the [Protégé Desktop](https://protege.stanford.edu) ontology editor that provides a service for the
[Protege Justification Explanation](https://github.com/liveontologies/protege-justification-explanation) plug-in. 
This service computes justifications from proofs using the 
[Proof Utility Library PuLI](https://github.com/liveontologies/puli).

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

To develop extensions to be used with this plug-in, use the following Maven dependency:

```
<dependency>
  <groupId>io.github.liveontologies</groupId>
  <artifactId>protege-proof-justification</artifactId>
  <version>${releasedVersion.version}</version>
</dependency>
```

Each extension should be a plug-in that implements the new 
extension points specified in
[`src/main/resources/plugin.xml`](https://github.com/liveontologies/protege-proof-justification/blob/main/src/main/resources/plugin.xml?raw=true)
using which proofs for entailments can be obtained.

See [Plugin Anatomy](https://protegewiki.stanford.edu/wiki/PluginAnatomy) for general
information about developing Protégé plug-ins.

To use snapshots versions of this library (if not compiled from sources), please add
the Sonatype OSSRH snapshot repository either to your `pom.xml` or `settings.xml`:
```
<repositories>
  <repository>
    <id>ossrh-snapshots</id>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
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