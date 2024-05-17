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
package org.liveontologies.protege.justification.proof;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.liveontologies.protege.justification.proof.service.JustificationProofService;
import org.liveontologies.puli.Inference;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to keep track of the inputs for justification computation for a
 * particular entailed {@link OWLAxiom}
 * 
 * @author Yevgeny Kazakov
 */
public class JustificationProofManager {

	// logger for this class
	private static final Logger LOGGER_ = LoggerFactory
			.getLogger(JustificationProofManager.class);

	/**
	 * the manager for the registered proof services
	 */
	private final JustificationProofServiceManager serviceMan_;

	/**
	 * the entailement for which the proofs are managed
	 */
	private final OWLAxiom entailment_;

	/**
	 * the current proof plus justification of inferences for
	 * the {@link #entailment_}
	 */
	private JustifiedProof<?> justifierProof_ = null;

	/**
	 * the listeners to be notified when {@link #justifierProof_} is changed
	 */
	private final List<ChangeListener> listeners_ = new ArrayList<ChangeListener>();

	JustificationProofManager(JustificationProofServiceManager serviceMan,
			OWLAxiom entailment) {
		this.serviceMan_ = serviceMan;
		this.entailment_ = entailment;
	}

	/**
	 * @return the axiom for which the proofs are managed
	 */
	public OWLAxiom getEntailment() {
		return entailment_;
	}

	/**
	 * @return the proof services that can provide proofs for the managed
	 *         entailment
	 * 
	 * @see #getEntailment()
	 */
	public Collection<JustificationProofService<?>> getServices() {
		List<JustificationProofService<?>> result = new ArrayList<>();
		for (JustificationProofService<?> service : serviceMan_.getServices()) {
			if (service.hasProof(entailment_)) {
				result.add(service);
			}
		}
		return result;
	}

	/**
	 * Sets the {@link JustificationProofService} to be used for this
	 * entailment; it should be among those returned by {@link #getServices()}
	 * 
	 * @param <I>
	 *            the type of inferences used in the proofs
	 * @param proofService
	 *            the {@link JustificationProofService} to be used for computing
	 *            justifications for this entailment
	 * 
	 * @see #getEntailment()
	 * @see #getServices()
	 * @see #getJustifiedProof()
	 */
	public synchronized <I extends Inference<?>> void selectService(
			JustificationProofService<I> proofService) {
		justifierProof_ = new JustifiedProof<I>(
				proofService.computeProof(entailment_), proofService);
		int i = 0;
		try {
			for (; i < listeners_.size(); i++) {
				listeners_.get(i).justifiedProofChanged();
			}
		} catch (Throwable e) {
			LOGGER_.warn("Remove the listener due to an exception", e);
			removeListener(listeners_.get(i));
		}
	}

	public synchronized void addListener(ChangeListener listener) {
		listeners_.add(listener);
	}

	public synchronized void removeListener(ChangeListener listener) {
		listeners_.remove(listener);
	}

	public synchronized JustifiedProof<?> getJustifiedProof() {
		return justifierProof_;
	}

	public interface ChangeListener {
		/**
		 * fired when a subsequent call to
		 * {@link JustificationProofManager#getJustifiedProof()} could return a
		 * different result
		 */
		void justifiedProofChanged();
	}

}
