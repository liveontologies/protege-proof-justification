package io.github.liveontologies.protege.justification.proof;

import javax.swing.JPanel;

import org.liveontologies.protege.explanation.justification.service.JustificationComputation;
import org.liveontologies.protege.explanation.justification.service.JustificationComputation.InterruptMonitor;

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

import org.liveontologies.protege.explanation.justification.service.JustificationComputationManager;
import org.liveontologies.protege.explanation.justification.service.JustificationListener;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Alexander Stupnikov Date: 10/02/2017
 * 
 * @author Yevgeny Kazakov
 */
public class ProofJustificationComputationManager
		extends JustificationComputationManager
		implements JustificationProofManager.ChangeListener {

	private final JustificationProofManager manager_;

	public ProofJustificationComputationManager(OWLAxiom entailment,
			JustificationListener listener, InterruptMonitor monitor,
			JustificationProofServiceManager manager) {
		super(entailment, listener, monitor);
		manager_ = new JustificationProofManager(manager, entailment);
		manager_.addListener(this);
	}

	@Override
	public JPanel getSettingsPanel() {
		return new JustificationProofServiceSelectionPanel(manager_);
	}

	@Override
	public JustificationComputation getComputation() {
		return new ProofJustificationComputation(manager_,
				getJustificationListener(), getInterruptMonitor());
	}

	@Override
	public void justifiedProofChanged() {
		notifyJustificationsOutdated();
	}
}