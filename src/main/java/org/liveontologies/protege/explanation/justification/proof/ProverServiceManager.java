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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.liveontologies.protege.explanation.justification.proof.service.ProverPlugin;
import org.liveontologies.protege.explanation.justification.proof.service.ProverPluginLoader;
import org.liveontologies.protege.explanation.justification.proof.service.ProverService;
import org.protege.editor.core.Disposable;

/**
 * Keeps track of the available specified {@link ProverService} plugins.
 * 
 * @author Pavel Klinov pavel.klinov@uni-ulm.de
 * 
 * @author Yevgeny Kazakov
 * 
 *         Date: 23/02/2017
 */

public class ProverServiceManager implements Disposable {

	public static String LAST_CHOOSEN_SERVICE_ID = null;

	private final Collection<ProverService> services_;
	private final Map<ProverService, String> serviceIds_;
	private ProverService selectedService_ = null;

	public ProverServiceManager(String KEY, String ID) throws Exception {
		services_ = new ArrayList<ProverService>();
		serviceIds_ = new HashMap<ProverService, String>();
		ProverPluginLoader loader = new ProverPluginLoader(KEY, ID);
		for (ProverPlugin plugin : loader.getPlugins()) {
			ProverService service = plugin.newInstance();
			services_.add(service);
			serviceIds_.put(service,
					plugin.getIExtension().getUniqueIdentifier());
		}
	}

	@Override
	public void dispose() throws Exception {
		for (ProverService service : services_) {
			service.dispose();
		}
	}

	public Collection<ProverService> getServices() {
		return services_;
	}

	public ProverService getSelectedService() {
		return selectedService_;
	}

	public void selectService(ProverService service) {
		selectedService_ = service;
		LAST_CHOOSEN_SERVICE_ID = getIdForService(service);
	}

	public String getIdForService(ProverService service) {
		return serviceIds_.get(service);
	}
}