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
package io.github.liveontologies.protege.justification.proof;

import java.util.Set;

import org.liveontologies.protege.explanation.justification.service.JustificationComputation;
import org.liveontologies.protege.explanation.justification.service.JustificationListener;
import org.liveontologies.protege.explanation.justification.service.JustificationPriorityComparator;
import org.liveontologies.puli.Inference;
import org.liveontologies.puli.pinpointing.InterruptMonitor;
import org.liveontologies.puli.pinpointing.MinimalSubsetEnumerator;
import org.liveontologies.puli.pinpointing.MinimalSubsetEnumerators;
import org.liveontologies.puli.pinpointing.PriorityComparator;
import org.semanticweb.owlapi.model.OWLAxiom;

public class ProofJustificationComputation extends JustificationComputation
		implements InterruptMonitor,
		MinimalSubsetEnumerator.Listener<OWLAxiom> {

	private final JustificationProofManager manager_;

	private PriorityComparator<Set<OWLAxiom>, ?> priorityComparator_;

	public ProofJustificationComputation(JustificationProofManager manager,
			JustificationListener listener,
			JustificationComputation.InterruptMonitor monitor) {
		super(listener, monitor);
		this.manager_ = manager;
	}

	@Override
	public <P> boolean setPrefferredPriority(
			JustificationPriorityComparator<P> comparator) {
		this.priorityComparator_ = new PriorityComparator<Set<OWLAxiom>, P>() {

			@Override
			public int compare(P p1, P p2) {
				return comparator.compare(p1, p2);
			}

			@Override
			public P getPriority(Set<OWLAxiom> justification) {
				return comparator.getPriority(justification);
			}
		};
		return true;
	}

	@Override
	public void startComputation() {
		enumerateJustifications(manager_.getEntailment(),
				manager_.getJustifiedProof());
	}

	public <I extends Inference<?>> void enumerateJustifications(Object goal,
			JustifiedProof<I> justifiedProof) {
		if (priorityComparator_ == null) {
			MinimalSubsetEnumerators.enumerateJustifications(goal,
					justifiedProof.getProof(), justifiedProof.getJustifier(),
					this, this);
		} else {
			MinimalSubsetEnumerators.enumerateJustifications(goal,
					justifiedProof.getProof(), justifiedProof.getJustifier(),
					priorityComparator_, this, this);
		}
	}

	@Override
	public void newMinimalSubset(Set<OWLAxiom> set) {
		notifyJustificationFound(set);
	}

}
