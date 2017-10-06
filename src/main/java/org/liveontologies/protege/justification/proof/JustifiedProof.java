package org.liveontologies.protege.justification.proof;

/*-
 * #%L
 * Protege Proof Justification
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2016 - 2017 Live Ontologies Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Set;

import org.liveontologies.puli.Inference;
import org.liveontologies.puli.InferenceJustifier;
import org.liveontologies.puli.Proof;
import org.semanticweb.owlapi.model.OWLAxiom;

class JustifiedProof<I extends Inference<?>> {

	private final Proof<? extends I> proof_;

	private final InferenceJustifier<? super I, Set<OWLAxiom>> justifier_;

	JustifiedProof(Proof<? extends I> proof,
			InferenceJustifier<? super I, Set<OWLAxiom>> justifier) {
		this.proof_ = proof;
		this.justifier_ = justifier;
	}

	Proof<? extends I> getProof() {
		return proof_;
	}

	InferenceJustifier<? super I, Set<OWLAxiom>> getJustifier() {
		return justifier_;
	}

}
