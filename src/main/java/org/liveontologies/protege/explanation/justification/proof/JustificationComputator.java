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
 * 
 * @author Alexander
 * Date: 10/02/2017
 */

public class JustificationComputator extends JustificationComputation {

	private boolean isInterrupted = false;
	private OWLEditorKit kit;
	private ProverServiceManager manager;
	private JustificationLogic logic;

	public JustificationComputator(OWLAxiom entailment, OWLEditorKit kit, ProverServiceManager manager) {
		super(entailment);
		this.kit = kit;
		this.manager = manager;
		this.logic = new JustificationLogic(kit);
	}

	@Override
	public void startComputation() {
		logic.computeProofBasedJustifications(getEntailment(), manager.getSelectedService().getProver(kit));
	}

	@Override
	public void interruptComputation() {
		logic.interruptComputation();
	}

	@Override
	public boolean isComputationInterrupted() {
		return logic.isComputationInterrupted();
	}

	@Override
	public void addComputationListener(JustificationComputationListener listener) {
		logic.addComputationListener(listener);
	}

	@Override
	public void removeComputationListener(JustificationComputationListener listener) {
		logic.removeComputationListener(listener);
	}
}