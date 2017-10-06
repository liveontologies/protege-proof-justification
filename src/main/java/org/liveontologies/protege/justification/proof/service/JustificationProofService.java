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
package org.liveontologies.protege.justification.proof.service;

import java.util.Set;

import org.liveontologies.puli.Inference;
import org.liveontologies.puli.InferenceJustifier;
import org.liveontologies.puli.Inferences;
import org.liveontologies.puli.Proof;
import org.liveontologies.puli.Proofs;
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
 * 
 * @param <I>
 *            type of inferences used in the proofs
 */
public abstract class JustificationProofService<I extends Inference<?>>
		implements ProtegePluginInstance, InferenceJustifier<I, Set<OWLAxiom>> {

	/**
	 * @param entailment
	 * @return {@code true} if this service can provide a proof for the given
	 *         entailed {@link OWLAxiom}; the subsequent call of
	 *         {@link #computeProof(OWLAxiom)} should return such a proof
	 */
	public abstract boolean hasProof(OWLAxiom entailment);

	/**
	 * Returns a {@link Proof} using which it is possible to derive the given
	 * {@link OWLAxiom} entailment. The proof may use any kind of inferences
	 * with any type of conclusions, with the only requirement that the given
	 * {@link OWLAxiom} is derived only if it is entailed by the ontology. If
	 * the {@link OWLAxiom} is not entailed, any {@link Proof} in which it is
	 * not derivable can be returned, e.g., the empty proof. The returned proof
	 * must be justification-complete, i.e., if the given {@link OWLAxiom} is
	 * entailed from some subset of the axioms in the ontology, it is possible
	 * to derive this {@link OWLAxiom} using only inferences whose justification
	 * {@link OWLAxiom} appear in this subset.
	 * 
	 * @param entailment
	 *            the {@link OWLAxiom} to be derived
	 * @return the {@link Proof} that derives the given {@link OWLAxiom}
	 * @throws UnsupportedEntailmentTypeException
	 *             if checking entailment of the given given {@link OWLAxiom} is
	 *             not supported
	 * 
	 * @see Proofs#isDerivable(Proof, Object)
	 * @see Proofs#emptyProof()
	 * @see #getJustification(Inference)
	 */
	public abstract Proof<? extends I> computeProof(OWLAxiom entailment);

	/**
	 * Provides a justification for each inference used in the proof
	 * 
	 * @param inference
	 * @return the set of {@link OWLAxiom}s occurring in the ontology that have
	 *         been used to trigger the given inference, that is, without these
	 *         axioms the inference would not be valid. The axioms of the
	 *         justification may not necessarily be the premises of this
	 *         inference. The formal requirement is that an {@link OWLAxiom} can
	 *         be derived using the inferences only if it is entailed from the
	 *         union of justifications for these inferences.
	 */
	@Override
	public abstract Set<OWLAxiom> getJustification(I inference);

	JustificationProofService<I> setup(OWLEditorKit kit) {
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