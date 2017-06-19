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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.liveontologies.protege.justification.proof.service.ProverPlugin;
import org.liveontologies.protege.justification.proof.service.ProverPluginLoader;
import org.liveontologies.protege.justification.proof.service.JustificationProofService;
import org.protege.editor.core.Disposable;
import org.protege.editor.owl.OWLEditorKit;

/**
 * Keeps track of the available specified {@link JustificationProofService} plugins.
 * 
 * @author Pavel Klinov pavel.klinov@uni-ulm.de
 * 
 * @author Yevgeny Kazakov
 * 
 *         Date: 23/02/2017
 */

public class ProverServiceManager implements Disposable {

	public static String LAST_CHOOSEN_SERVICE_ID = null;

	private final Collection<JustificationProofService> services_;
	private final Map<JustificationProofService, String> serviceIds_;
	private JustificationProofService selectedService_ = null;

	public ProverServiceManager(OWLEditorKit kit) throws Exception {
		services_ = new ArrayList<JustificationProofService>();
		serviceIds_ = new HashMap<JustificationProofService, String>();
		ProverPluginLoader loader = new ProverPluginLoader(kit);
		for (ProverPlugin plugin : loader.getPlugins()) {
			JustificationProofService service = plugin.newInstance();
			services_.add(service);
			serviceIds_.put(service,
					plugin.getIExtension().getUniqueIdentifier());
		}
	}

	@Override
	public void dispose() throws Exception {
		for (JustificationProofService service : services_) {
			service.dispose();
		}
	}

	public Collection<JustificationProofService> getServices() {
		return services_;
	}

	public JustificationProofService getSelectedService() {
		return selectedService_;
	}

	public void selectService(JustificationProofService service) {
		selectedService_ = service;
		LAST_CHOOSEN_SERVICE_ID = getIdForService(service);
	}

	public String getIdForService(JustificationProofService service) {
		return serviceIds_.get(service);
	}
}