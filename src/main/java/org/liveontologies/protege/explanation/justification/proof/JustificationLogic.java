package org.liveontologies.protege.explanation.justification.proof;

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


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.liveontologies.owlapi.proof.OWLProver;
import org.liveontologies.protege.explanation.justification.service.JustificationComputationListener;
import org.liveontologies.puli.InferenceJustifier;
import org.liveontologies.puli.InferenceSet;
import org.liveontologies.puli.InferenceSets;
import org.liveontologies.puli.justifications.InterruptMonitor;
import org.liveontologies.puli.justifications.MinimalSubsetEnumerator;
import org.liveontologies.puli.justifications.ResolutionJustificationComputation;
import org.protege.editor.owl.OWLEditorKit;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.reasoner.InferenceType;

/**
 * 
 * @author Alexander
 * Date: 13/02/2017
 */

public class JustificationLogic {

	private OWLEditorKit editorKit;
	private List<JustificationComputationListener> listeners;
	private boolean isInterrupted = false;
	
	public JustificationLogic(OWLEditorKit ek) {
		this.editorKit = ek;
		listeners = new ArrayList<JustificationComputationListener>();
	}

	public void computeProofBasedJustifications(OWLAxiom entailment, OWLProver prover) {
		if (prover == null)
			return;

		prover.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		InferenceSet<OWLAxiom> proof = prover.getProof(entailment);
		Set<OWLAxiom> axioms = prover.getRootOntology().getAxioms(Imports.INCLUDED);
		InferenceSet<OWLAxiom> inferenceSet = InferenceSets.addAssertedInferences(proof, axioms);

		InferenceJustifier<OWLAxiom, ? extends Set<? extends OWLAxiom>> justifier = InferenceSets
				.justifyAssertedInferences();

		ResolutionJustificationComputation.Factory<OWLAxiom, OWLAxiom> computationFactory = ResolutionJustificationComputation
				.getFactory();
		SimpleMonitor monitor = new SimpleMonitor();
		final MinimalSubsetEnumerator.Factory<OWLAxiom, OWLAxiom> computation = computationFactory.create(inferenceSet,
				justifier, monitor);

		SimpleListener listener = new SimpleListener();
		computation.newEnumerator(entailment).enumerate(listener);
	}
	
	public void addComputationListener(JustificationComputationListener listener) {
		listeners.add(listener);
	}

	public void removeComputationListener(JustificationComputationListener listener) {
		listeners.remove(listener);
	}
	
	public void interruptComputation() {
		isInterrupted = true;
	}

	public boolean isComputationInterrupted() {
		return isInterrupted;
	}

	private class SimpleListener implements MinimalSubsetEnumerator.Listener<OWLAxiom> {

		@Override
		public void newMinimalSubset(Set<OWLAxiom> justification) {
			for (JustificationComputationListener listener : listeners)
				listener.foundJustification(justification);
		}
	}
	
	private class SimpleMonitor implements InterruptMonitor {
		
		@Override
		public boolean isInterrupted() {
			return isComputationInterrupted();
		}
	}
}
