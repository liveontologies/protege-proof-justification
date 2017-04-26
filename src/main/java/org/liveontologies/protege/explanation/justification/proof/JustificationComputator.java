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

import org.liveontologies.protege.explanation.justification.service.JustificationComputation;
import org.liveontologies.protege.explanation.justification.service.JustificationComputationListener;
import org.protege.editor.owl.OWLEditorKit;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Alexander Stupnikov Date: 10/02/2017
 */

public class JustificationComputator extends JustificationComputation {

	private final OWLEditorKit kit_;
	private final ProverServiceManager manager_;
	private final JustificationLogic logic_;

	public JustificationComputator(OWLAxiom entailment, OWLEditorKit kit,
			ProverServiceManager manager) {
		super(entailment);
		kit_ = kit;
		manager_ = manager;
		logic_ = new JustificationLogic();
	}

	@Override
	public void startComputation() {
		logic_.computeProofBasedJustifications(getEntailment(),
				manager_.getSelectedService().getProver(kit_));
	}

	@Override
	public void interruptComputation() {
		logic_.interruptComputation();
	}

	@Override
	public boolean isComputationInterrupted() {
		return logic_.isComputationInterrupted();
	}

	@Override
	public void addComputationListener(
			JustificationComputationListener listener) {
		logic_.addComputationListener(listener);
	}

	@Override
	public void removeComputationListener(
			JustificationComputationListener listener) {
		logic_.removeComputationListener(listener);
	}
}