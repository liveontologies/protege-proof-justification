package org.liveontologies.protege.justification.proof.service;

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

import org.liveontologies.puli.InferenceJustifier;
import org.liveontologies.puli.Proof;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * A {link Proof} that can be used for computing the justifications for the
 * given goal conclusion. Every inference used in this proof is associated with
 * a set of {@link OWLAxiom}s that are necessary to trigger this inferences.
 * Justifications are minimal subsets of {@link OWLAxiom}s such that the given
 * goal conclusion {@link #getGoal()} can be derived using the inferences in the
 * {link Proof} that are associated with subsets of such axioms.
 * 
 * @author Yevgeny Kazakov
 *
 * @param <C>
 */
public interface JustificationCompleteProof<C>
		extends Proof<C>, InferenceJustifier<C, Set<? extends OWLAxiom>> {

	/**
	 * @return the conclusion for which the justifications should be computed
	 */
	C getGoal();
}