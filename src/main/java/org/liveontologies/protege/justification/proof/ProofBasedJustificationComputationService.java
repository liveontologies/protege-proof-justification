package org.liveontologies.protege.justification.proof;

import org.liveontologies.protege.explanation.justification.service.JustificationComputation.InterruptMonitor;

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

import org.liveontologies.protege.explanation.justification.service.JustificationComputationManager;
import org.liveontologies.protege.explanation.justification.service.JustificationComputationService;
import org.liveontologies.protege.explanation.justification.service.JustificationListener;
import org.liveontologies.protege.justification.proof.service.JustificationProofService;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Alexander Stupnikov Date: 10/02/2017
 */

public class ProofBasedJustificationComputationService
		extends JustificationComputationService {

	JustificationProofServiceManager manager_;

	@Override
	public JustificationComputationManager createComputationManager(
			OWLAxiom entailment, JustificationListener listener,
			InterruptMonitor monitor) {
		return new ProofJustificationComputationManager(entailment, listener,
				monitor, manager_);
	}

	@Override
	public void initialise() throws Exception {
		manager_ = JustificationProofServiceManager.get(getOWLEditorKit());
	}

	@Override
	public boolean canJustify(OWLAxiom entailment) {
		for (JustificationProofService service : manager_.getServices()) {
			if (service.hasProof(entailment)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void dispose() {
	}

	@Override
	public String getName() {
		return "Proof Justifications";
	}

}