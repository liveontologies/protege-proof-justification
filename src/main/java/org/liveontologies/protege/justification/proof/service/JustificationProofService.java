package org.liveontologies.protege.justification.proof.service;

/*-
 * #%L
 * Protege Proof Justification Explanation
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

import org.protege.editor.core.plugin.ProtegePluginInstance;
import org.protege.editor.owl.OWLEditorKit;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException;

/**
 * A skeleton for a plugin that can provide proofs that can be used for
 * computing justifications
 * 
 * @author Alexander Date: 23/02/2017
 * @author Yevgeny Kazakov
 */

public abstract class JustificationProofService
		implements ProtegePluginInstance {

	/**
	 * @param entailment
	 * @return {@code true} if this service can provide a proof for the given
	 *         entailed {@link OWLAxiom}; the subsequent call of
	 *         {@link #computeProof(OWLAxiom)} should return such a proof
	 */
	public abstract boolean hasProof(OWLAxiom entailment);

	/**
	 * Returns a {@link JustificationCompleteProof} for the given
	 * {@link OWLAxiom} entailment from which it is possible to recognize if the
	 * entailment can be derived from subsets of {@link OWLAxiom}s of the
	 * ontology.
	 * 
	 * @param entailment
	 *            the {@link OWLAxiom} for which the proof should be generated
	 * @return the {@link JustificationCompleteProof} representing the set of
	 *         inferences using which the given {@link OWLAxiom} can be derived
	 *         from the axioms in the ontology
	 * @throws UnsupportedEntailmentTypeException
	 *             if checking entailment of the given given {@link OWLAxiom} is
	 *             not supported
	 */
	public abstract JustificationCompleteProof<?> computeProof(OWLAxiom entailment);

	JustificationProofService setup(OWLEditorKit kit) {
		kit_ = kit;
		return this;
	}

	private OWLEditorKit kit_;

	public OWLEditorKit getEditorKit() {
		return kit_;
	}

	/**
	 * Should return a name for the plugin
	 * 
	 * @return the name to be displayed in available plugins list
	 */
	public abstract String getName();

	@Override
	public String toString() {
		return getName();
	}
}