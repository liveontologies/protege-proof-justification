package org.liveontologies.protege.justification.proof;

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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.liveontologies.protege.justification.proof.service.JustificationProofService;

class JustificationProofServiceSelectionPanel extends JPanel {

	private static final long serialVersionUID = 6761454714925903936L;

	/**
	 * 
	 */
	private final JustificationProofManager proofManager_;

	public JustificationProofServiceSelectionPanel(
			JustificationProofManager proofManager) {
		this.proofManager_ = proofManager;
		Collection<JustificationProofService<?>> services = proofManager
				.getServices();
		JustificationProofService<?> selectedService;
		switch (services.size()) {
		case 0:
			break;
		case 1:
			selectedService = services.iterator().next();
			proofManager_.selectService(selectedService);
			setLayout(new BorderLayout());
			JLabel label = new JLabel(
					"Using " + selectedService + " as a proof service");
			add(label, BorderLayout.NORTH);
			break;
		default:
			setLayout(new BorderLayout());
			selectedService = services.iterator().next();
			JComboBox<JustificationProofService<?>> selector = new JComboBox<JustificationProofService<?>>();
			for (JustificationProofService<?> service : services) {
				selector.addItem(service);
				// TODO: cache the last selection
			}
			selector.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					proofManager_.selectService(
							(JustificationProofService<?>) selector
									.getSelectedItem());
				}
			});
			add(selector, BorderLayout.NORTH);
			selector.setSelectedItem(selectedService);
		}
	}
}