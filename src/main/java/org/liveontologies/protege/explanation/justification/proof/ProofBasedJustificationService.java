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


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.liveontologies.protege.explanation.justification.proof.service.ProverService;
import org.liveontologies.protege.explanation.justification.service.ComputationService;
import org.liveontologies.protege.explanation.justification.service.JustificationComputation;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * 
 * @author Alexander
 * Date: 10/02/2017
 */

public class ProofBasedJustificationService extends ComputationService {

	private ProverServiceManager manager;
	private ProverSwitchPanel panel;
	
	@Override
	public JustificationComputation createJustificationComputation(OWLAxiom entailment) {
		return new JustificationComputator(entailment, getOWLEditorKit(), manager);
	}
	
	public boolean canComputeJustification(OWLAxiom entailment) {
		return manager.getServices().size() != 0;
	}

	@Override
	public void initialise() throws Exception {
		manager = new ProverServiceManager("org.liveontologies.protege.explanation.justification.proof", "ProverService");
		panel = manager.getServices().size() == 0 ? null : new ProverSwitchPanel();
	}

	@Override
	public void dispose() {
	}

	@Override
	public String getName() {
		return "Proof Based Justification";
	}
	
	@Override
	public JPanel getSettingsPanel() {
		return panel;
	}
	
	private class ProverSwitchPanel extends JPanel {
		
		public ProverSwitchPanel() {
			Collection<ProverService> services = manager.getServices();
			switch (services.size()) {
			case 0:
				break;
			case 1:
				manager.selectService(services.iterator().next());
				setLayout(new BorderLayout());
				JLabel label = new JLabel("Using " + manager.getSelectedService() + " as a prover service");
				add(label, BorderLayout.NORTH);
				break;
			default:
				setLayout(new BorderLayout());
				ProverService serviceToSelect = services.iterator().next();
				JComboBox<ProverService> selector = new JComboBox<ProverService>();
				for (ProverService service : services)
				{
					selector.addItem(service);
					if (ProverServiceManager.lastChoosenServiceId == manager.getIdForService(service))
						serviceToSelect = service;
				}
				selector.setSelectedItem(serviceToSelect);
				manager.selectService(serviceToSelect);
				selector.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						manager.selectService((ProverService) selector.getSelectedItem());
						settingsChanged();
					}
				});
				add(selector, BorderLayout.NORTH);
			}
		}
	}
}