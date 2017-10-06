package org.liveontologies.protege.justification.proof.preferences;

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
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.liveontologies.protege.justification.proof.JustificationProofServiceManager;
import org.liveontologies.protege.justification.proof.service.JustificationProofService;
import org.protege.editor.core.ui.preferences.PreferencesLayoutPanel;
import org.protege.editor.owl.ui.preferences.OWLPreferencesPanel;

public class ProofJustificationPreferencesGeneralPanel
		extends OWLPreferencesPanel {

	private static final long serialVersionUID = 6339983846736926930L;

	@Override
	public void initialise() throws Exception {
		setLayout(new BorderLayout());
		PreferencesLayoutPanel panel = new PreferencesLayoutPanel();
		add(panel, BorderLayout.NORTH);
		addInstalledJustificationProofServicesComponent(panel);
	}

	@Override
	public void dispose() throws Exception {
	}

	@Override
	public void applyChanges() {
	}

	private void addInstalledJustificationProofServicesComponent(
			PreferencesLayoutPanel panel) throws Exception {
		panel.addGroup("Installed proof services");
		DefaultListModel<JustificationProofService<?>> servicesModel = new DefaultListModel<>();
		Collection<JustificationProofService<?>> services = JustificationProofServiceManager
				.get(getOWLEditorKit()).getServices();
		for (JustificationProofService<?> service : services) {
			servicesModel.addElement(service);
		}
		JList<JustificationProofService<?>> proofServicesList = new JList<>(
				servicesModel);
		proofServicesList.setToolTipText(
				"Plugins that provide proofs that are used for justification computation");
		JScrollPane pluginInfoScrollPane = new JScrollPane(proofServicesList);
		pluginInfoScrollPane.setPreferredSize(new Dimension(300, 100));
		panel.addGroupComponent(pluginInfoScrollPane);
	}
}