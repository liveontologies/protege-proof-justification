package org.liveontologies.protege.justification.proof;

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

import org.liveontologies.protege.explanation.justification.service.JustificationComputationListener;
import org.liveontologies.protege.justification.proof.service.JustificationCompleteProof;
import org.liveontologies.protege.justification.proof.service.ProverService;
import org.liveontologies.puli.justifications.InterruptMonitor;
import org.liveontologies.puli.justifications.MinimalSubsetEnumerator;
import org.liveontologies.puli.justifications.MinimalSubsetEnumerators;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Alexander Stupnikov Date: 13/02/2017
 */

public class JustificationLogic {

	private final List<JustificationComputationListener> listeners_;
	private boolean isInterrupted_ = false;

	public JustificationLogic() {
		listeners_ = new ArrayList<JustificationComputationListener>();
	}

	public <C> void computeProofBasedJustifications(OWLAxiom entailment,
			ProverService<C> service) {
		JustificationCompleteProof<C> jcp = service.getJustificationCompleteProof(entailment);
		SimpleMonitor monitor = new SimpleMonitor();
		SimpleListener listener = new SimpleListener();
		MinimalSubsetEnumerators.enumerateJustifications(jcp.getGoal(), jcp,
				jcp, monitor, listener);
	}

	public void addComputationListener(
			JustificationComputationListener listener) {
		listeners_.add(listener);
	}

	public void removeComputationListener(
			JustificationComputationListener listener) {
		listeners_.remove(listener);
	}

	public void interruptComputation() {
		isInterrupted_ = true;
	}

	public boolean isComputationInterrupted() {
		return isInterrupted_;
	}

	private class SimpleListener
			implements MinimalSubsetEnumerator.Listener<OWLAxiom> {

		@Override
		public void newMinimalSubset(Set<OWLAxiom> justification) {
			for (JustificationComputationListener listener : listeners_)
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
