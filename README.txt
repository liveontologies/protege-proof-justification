Protege Proof Justification Explanation

Protege Proof Justification Explanation is Copyright (c) 2016 - 2017 
Live Ontologies Project

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

ABOUT:

This is a plug-in that provides the Protege Justification Explanation plug-in with
proof-based justifications by registering as an extension to its justification service.
It defines an extension point to obtain a prover also since it does not compute proofs
by its own but uses the chosen prover instead. The main functionality of this plug-in 
is computing justifications based on proof and providing the Protege Justification
Explanation plug-in with them.

For further information see: 

https://github.com/liveontologies/protege-proof-justification-explanation 

REQUIREMENTS:

Protege Proof Justification Explanation is tested to work with Protege 5.0.0. It may work 
with other versions of Protege.

INSTALLATION:

To install, place all jar files inside the archive 

	protege-proof-justification-explanation-0.0.1-SNAPSHOT.zip 

to the plugins folder of the Protege installation. The plug-in supports Protege
Auto Update feature which can be used for upgrading to newer versions according
to the instructions here:

    http://protegewiki.stanford.edu/wiki/EnablePluginAutoUpdate